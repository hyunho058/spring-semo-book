package com.semobook.recom.service;

import com.semobook.book.domain.Book;
import com.semobook.book.repository.BookRepository;
import com.semobook.common.SemoConstant;
import com.semobook.common.StatusEnum;
import com.semobook.recom.domain.*;
import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.repository.*;
import com.semobook.user.dto.UserInfoDto;
import com.semobook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static com.semobook.common.SemoConstant.CATEGORY_TYPE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecomService {

    private final BookRepository bookRepository;
    private final RecomBestSellerRepository bestSellerRepository;
    private final RecomUserInfoRepository userInfoRepository;
    private final RecomUserReviewRepository userReviewRepository;
    private final RecomUserTotalRepository recomUserTotalRepository;
    private final UserPriorityRedisRepository userPriorityRedisRepository;
    private final UserRepository userRepository;
    private final AllReviewRepository allReviewRepository;
    Map<String, Integer> categoryIndex;

    /**
     * 초기세팅 : 인덱스별 값
     */
    @PostConstruct
    private void init() {
        categoryIndex = new HashMap<>();

        for (int i = 0; i < CATEGORY_TYPE.length; i++) {
            categoryIndex.put(CATEGORY_TYPE[i], 1);
        }

    }

    /**
     * 유저가 읽은 책 기반 추천
     *
     * @author hyejinzz
     * @since 2021-06-01
     **/

    public RecomResponse getUserReviewRecom(long userid) {
        Object data = null;
        StatusEnum hCode = StatusEnum.hd4444;
        String hMessage = "test";
        try {
            userid = 976;
            Optional<RecomUserReview> f = userReviewRepository.findById(userid);
            log.info(f.toString());
            data = f;
        } catch (Exception e) {

        }
        return RecomResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


    /**
     * 유저가 리뷰를 등록하면 관련도서 추천을 함
     * 1. 책에 keyword 있으면 keyword 같은 키워드 탐색
     * 2. 값이 부족하면 category로 같은 도서 탐색
     * 3. 값이 부족하면 kdc로 앞자리 같은 도서 탐색
     * 20개 채워지면 redis에 저장
     * 20개 채워지지 않으면 저장하지 않음
     *
     * @author hyejinzz
     * @since 2021-06-01
     **/
    // TODO: 2021-06-01 LIST<BOOK> 으로 만들어야한다
    public void updateUserReviewRecom(String isbn, long userNo) {
        Book book = bookRepository.findByIsbn(isbn);

        List<Book> bookList = new ArrayList<>();

        String bookName = book.getBookName();
        String author = book.getAuthor();
        String publisher = book.getPublisher();
        String kdc = book.getKdc();
        String category = book.getCategory();
        String keyword = book.getKeyword();
        String img = book.getImg();

//        //내가 읽은 책의 카테고리 가져온다.

        if (book.getKeyword() != null) {
            List<Book> recomBookList = bookRepository.findAllByKeyword(category);
            bookList.addAll(recomBookList);
        }
        if (category != null && bookList.size() < 20) {
            //카테고리 같은 책들 가져오기
            List<Book> recomBookList = bookRepository.findAllByCategory(category);
            bookList.addAll(recomBookList);
        }

        try {
            userReviewRepository.save(RecomUserReview.builder()
                    .userNo(userNo)
                    .isbn(isbn)
                    .bookName(bookName)
                    .author(author)
                    .publisher(publisher)
                    .kdc(kdc)
                    .category(category)
                    .keyword(keyword)
                    .img(img)
                    .build());
        } catch (Exception e) {
            log.info(":: updateUserReviewRecom err :: error is {} ", e);
        }

    }

    /**
     * 1. redis 조회
     * 2. db 조회, redis 저장
     * 3. db, redis 없으면 base 호출
     * 4. 있으면 성향 비율별 조회
     * 5. 없으면 카테고리 정보
     *
     * @author hyejinzz
     * @since 2021-06-19
     **/
    public RecomResponse userRandomEvaluation(long userId) {

        List<String> userPriority = new ArrayList<>();
        List<RecomBestSeller> bestSellersList = new ArrayList<>();
        Object data = null;
        StatusEnum hCode = null;
        String hMessage = null;
        try {
            hCode = StatusEnum.hd1004;
            userPriority = getUserPriority(userId);

            if (userPriority.size() == 0) {
                bestSellersList = basicEvaluation();
            }
            if (userPriority.size() > 0) {
                // 성향별로 가져오기
                bestSellersList = userEvaluation(userPriority);
            }
            if (bestSellersList.size() > 0 && bestSellersList.get(0) == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "userRandomEvaluation fail";
            }
            if (bestSellersList.size() > 0 && bestSellersList.get(0) != null) {
                data = bestSellersList;
                log.info(":: userRandomEvaluation :: data is {} ", data);
                hMessage = "userRandomEvaluation 성공";
            }
        } catch (Exception e) {
            hCode = StatusEnum.hd4444;
            hMessage = "userRandomEvaluation 에러";
            log.error(":: userRandomEvaluation err :: error is {} ", e);
        }
        return RecomResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    /**
     * 우선순위에 따라 호출하기
     * 우선순위가 5개가 안되면 나머지는 종합 베스트셀러 호출
     *
     * @param userPriority
     * @return
     */
    private List<RecomBestSeller> userEvaluation(List<String> userPriority) {
        Map<String, Integer> goalMap = new HashMap<>();
        List<RecomBestSeller> list = new ArrayList<>();
        for (int i = 0; i < userPriority.size(); i++) {
            switch (i + 1) {
                case 1:
                    goalMap.put(userPriority.get(i), SemoConstant.FIRST_PRIORITY_RATIO);
                    break;
                case 2:
                    goalMap.put(userPriority.get(i), SemoConstant.SECOND_PRIORITY_RATIO);
                    break;
                case 3:
                    goalMap.put(userPriority.get(i) , SemoConstant.THRID_PRIORITY_RATIO);
                    break;
                case 4:
                    goalMap.put(userPriority.get(i) , SemoConstant.FIRTH_PRIORITY_RATIO);
                    break;
                case 5:
                    goalMap.put(userPriority.get(i), SemoConstant.FIFTH_PRIORITY_RATIO);
                    break;
            }
        }
        for (String s : goalMap.keySet()) {
            list.addAll(getBestSellerList(s, goalMap.get(s)));
        }

        //부족한 개수 채우기
        if (list.size() < 20) {
            int searchSize = 20 - list.size();
            list.addAll(getBestSellerList("A_", searchSize));
        }

        return list;
    }


    /**
     * 유저 성향 가져오기 리뷰데이터->redis -> db
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    private List<String> getUserPriority(long userId) {
        List<String> userPriority = new ArrayList<>();
        //리뷰데이터로 새로운 성향 만들기
        makeUserPriority(userId);
        //redis
        String value;
        UserPriorityRedis userPriorityRedis = userPriorityRedisRepository.findById(userId).orElse(new UserPriorityRedis());
        //Database
        if (userPriorityRedis.getValue() == null) {
            UserInfoDto userInfo = new UserInfoDto(userRepository.findByUserNo(userId));
            if (userInfo == null) return userPriority;
            value = userInfo.getUserPriority();
            saveUserPriorityRedis(userId, value);
        }
        if (userPriorityRedis.getValue() != null) {
            value = userPriorityRedis.getValue();
            userPriority = Arrays.asList(value.split(":"));
        }
        return userPriority;
    }

    /**
     * redis에 임시로저장된 데이터를 바탕으로 새로운 priority 가져온다
     *
     * @param userId
     */
    private void makeUserPriority(long userId) {
        //redis에 없으면 그냥 return
        AllReview allReview = allReviewRepository.findById(userId).orElse(null);
        if(allReview !=null){
            createUserPriority(userId, allReview.getValue());
        }

    }


    /**
     * 유저 정보가 없을경우 BestSeller 분야별 (종합제외) 로 1개씩 가져오기
     *
     * @return
     */
    private List<RecomBestSeller> basicEvaluation() {
        List<RecomBestSeller> bookList = new ArrayList<>();
        for (String s : CATEGORY_TYPE) {
            int index = categoryIndex.get(s);
            bookList.add(getBestSeller(s));
        }
        bookList = bookListCutter(bookList);
        return bookList;
    }

    /**
     * 카테고리별 개수별로 가져오기
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    private List<RecomBestSeller> getBestSellerList(String cate, int num) {
        int index = categoryIndex.get(cate);
        List<RecomBestSeller> bookList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            bookList.add(getBestSeller(cate));
        }
        return bookList;
    }


    /**
     * 종류별 1개의 데이터를 가져온다
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    private RecomBestSeller getBestSeller(String key) {
        log.info(key);
        int idx = categoryIndex.get(key);
        RecomBestSeller bs = bestSellerRepository.findById(key + idx++).orElse(new RecomBestSeller());
        idx = idx > 20 ? 1 : idx;
        categoryIndex.put(key, idx);
        if (bs != null) {
            log.info(":: getFromRedis :: test is {} ", bs.getIsbn());
        }
        return bs == null ? new RecomBestSeller() : bs;

    }

    /**
     * 레디스 저장
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/

    private void saveUserPriorityRedis(long userId, String value) {
        UserPriorityRedis up = UserPriorityRedis.builder().userNo(userId).value(value).build();
        userPriorityRedisRepository.save(up);
    }

    /**
     * 이용자가 추천받고 싶지 않은 책들을 필터함
     * 유저가 이미 평가한 책들을 필터함
     *
     * @author hyejinzz
     * @since 2021-06-03
     **/

    private List<Book> bookListUserCutter(List<Book> recomList) {

//       recomList.stream().filter(i->)

        return null;
    }

    /**
     * 중복된 책 제거, 20권이 넘으면 삭제
     *
     * @author hyejinzz
     * @since 2021-06-19
     **/
    private List<RecomBestSeller> bookListCutter(List<RecomBestSeller> bookList) {
        bookList = bookList.stream().distinct().collect(Collectors.toList());
        if (bookList.size() > 20) {
            bookList = bookList.subList(0, 20);
        }
        return bookList;
    }

    /**
     * redis Date Update
     */
    private void updateDataFromRedis(long userId, ReviewInfo value) {
        AllReview find = allReviewRepository.findById(userId).orElse(null);
        List<ReviewInfo> findValue = null;
        if (find != null) {
            findValue = find.getValue();
        }
        if (findValue != null) {
            findValue.add(value);
            allReviewRepository.save(AllReview.builder()
                    .userId(userId)
                    .value(findValue)
                    .build());
        }

    }


    /**
     * 유저평점을 바탕으로 userpriority 만들기 (최신 5개만)
     *
     * @author hyejinzz
     * @since 2021-06-27
     **/

    private void createUserPriority(long userNo, List<ReviewInfo> value) {

        //4개보다 크면 최신 5개만 가져온다
        if (value.size() > SemoConstant.CHECK_USER_REVIEW_CNT) {
            int cutSize = SemoConstant.CHECK_USER_REVIEW_CNT + 1;
            value = value.subList(value.size() - cutSize, value.size());
        }
        Map<String, Integer> map = new HashMap<>();
        for (ReviewInfo reviewInfo : value) {
            String key = reviewInfo.getCategory();
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        List<String> sortKey = new ArrayList<>(map.keySet());
        Collections.sort(sortKey, (o1, o2) -> map.get(o2).compareTo(map.get(o1)));
        //redis 저장
        StringBuilder saveValue = new StringBuilder();
        for (String l : sortKey) {
            saveValue.append(l).append(SemoConstant.COLON);
        }
        saveValue.deleteCharAt(saveValue.lastIndexOf(SemoConstant.COLON));
        userPriorityRedisRepository.save(UserPriorityRedis.builder()
                .userNo(userNo)
                .value(saveValue.toString())
                .build());

    }


}