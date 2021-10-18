package com.semobook.recom.service;

import com.semobook.book.dto.BookDto;
import com.semobook.book.repository.BookRepository;
import com.semobook.book.service.BestSellerService;
import com.semobook.book.service.BookService;
import com.semobook.bookReview.domain.AllReview;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.bookwant.dto.BookWantDto;
import com.semobook.bookwant.dto.Preference;
import com.semobook.bookwant.repository.BookWantRepository;
import com.semobook.common.SemoConstant;
import com.semobook.common.StatusEnum;
import com.semobook.recom.domain.AdminRecom;
import com.semobook.recom.domain.RecomInfo;
import com.semobook.recom.domain.ReviewInfo;
import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.repository.AdminRecomRepository;
import com.semobook.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.semobook.tools.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class RecomService {

    private final AdminRecomRepository adminRecomRepository;
    private final BookService bookService;
    private final BookWantRepository bookWantRepository;
    private final BestSellerService bestSellerService;
    private final UserService userService;
    private final BookRepository bookRepository;
    private final AllReviewRepository allReviewRepository;

    /**
     * 유저별 추천
     *
     * @author hyunho
     * @since 2021/07/11
     **/
    public RecomResponse recomByUser(long userId) {

        Object data = null;
        StatusEnum hCode = null;
        String hMessage = null;
        try {

            List<RecomInfo> list = getTotalRecom(userId);
            data = list;
            hCode = StatusEnum.hd1004;
            hMessage = "recomByUser 성공";
        } catch (Exception e) {
            hCode = StatusEnum.hd4444;
            hMessage = "recomByUser 에러";
            log.error(":: recomByUser err :: error is {} ", e);
        }
        return RecomResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();

    }


    /**
     * 7개의 추천데이터중 최소 3~5개의 추천 데이터 List를 가져온디
     */
    private List<RecomInfo> getTotalRecom(long userId) {
        List<RecomInfo> recomInfoList = new ArrayList<>();
        //관리자 추천
        List<RecomInfo> adminRecomResult = adminRecom();
        if (!(adminRecomResult == null || adminRecomResult.size() == 0)) {
            recomInfoList.addAll(adminRecomResult);
        }
        RecomInfo getUserReviewRecomResult = getUserReviewRecom(userId);
        if (!(getUserReviewRecomResult == null || getUserReviewRecomResult.getBookInfoList().size() == 0)) {
            recomInfoList.add(getUserReviewRecomResult);
        }

//        recomInfoList.add(reviewRecom());
        //유저가 좋아요 누른 글 추천
        RecomInfo userWantRecomResult = userWantRecom(userId);
        if (!(userWantRecomResult == null || userWantRecomResult.getBookInfoList().size() == 0)) {
            recomInfoList.add(userWantRecomResult);
        }
//        recomInfoList.add(userInfoRecom());

        RecomInfo userCategoryRecomResult = userCategoryRecom(userId);
        if (!(userCategoryRecomResult == null || userCategoryRecomResult.getBookInfoList().size() == 0)) {
            recomInfoList.add(userCategoryRecomResult);
        }

        if (recomInfoList.size() < 3) {
            recomInfoList.add(bestSellerRecom());
            recomInfoList.add(steadySellerRecom());
        }
        return recomInfoList;
    }


    /**
     * 1. 관리자 직접 추천을 db에서 가져온다
     *
     * @author hyejinzz
     * @since 2021-06-30
     **/
    private List<RecomInfo> adminRecom() {
        //db조회
        //db 값 split해서 값 저장
        List<RecomInfo> recomInfoList = new ArrayList<>();
        List<AdminRecom> adminRecomList = adminRecomRepository.findAll();
        log.info(":: adminRecom :: adminRecomList is {} ", adminRecomList);

        /**
         * isbn이랑 title 꺼내기
         */
        for (AdminRecom adminRecom : adminRecomList) {
            recomInfoList.add(
                    RecomInfo.builder()
                            .title(adminRecom.getTitle())
                            .bookInfoList(getBookInfoByIsbnList(StringTools.stringConvToList(adminRecom.getIsbn(), SemoConstant.BACKSLASH_VARTICAL_BAR)))
                            .build()
            );

        }
        return recomInfoList;
    }


    /**
     * 2. 유저가 작성한 리뷰 기반으로 추천 (최신 한개)
     *
     * @author hyunho
     * @since 2021/07/11
     **/

    public RecomInfo getUserReviewRecom(long userNo) {
        List<BookDto> bookInfoList = new ArrayList<>();
        List<ReviewInfo> value;
        String recentCategory = null;
        String userPriorityValue = null;
        try {
            AllReview allReview = allReviewRepository.findById(userNo).orElse(null);


            if (allReview == null) {
                return null;
            }
            if (allReview != null) {
                value = allReview.getValue();
                for (ReviewInfo info : value) {
                    if (info.getPoint() >= 3) {
                        recentCategory = info.getCategory();
                    }
                }
            }
            if (recentCategory != null) {
                bookInfoList = bestSellerService.getBestSellerList(recentCategory, 10)
                        .stream()
                        .map(a -> BookDto.builder()
                                .isbn(a.getIsbn())
                                .bookName(a.getBookName())
                                .author(a.getAuthor())
                                .publisher(a.getPublisher())
                                .category(a.getCategory())
                                .img(a.getImg())
                                .build())
                        .collect(Collectors.toList());
                userPriorityValue = SemoConstant.CATEGORY_TYPE_MAP.get(recentCategory);
            }
        } catch (Exception e) {
            log.error(":: getUserReviewRecom err :: error is {} ", e);

        }
        if (bookInfoList.size() == 0) return null;
        else return RecomInfo.builder()
                .bookInfoList(bookInfoList)
                .title("회원님이 최근에 좋아한 " + userPriorityValue +" 분야의 책이에요")
                .build();
    }

    /**
     * 유저가 리뷰를 등록하면 관련도서 추천을 함
     *
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
//    public  void updateUserReviewRecom(String isbn, long userNo) {
//        //List<RecomInfo>
//        Book book = bookRepository.findByIsbn(isbn);
//
//        List<Book> bookList = new ArrayList<>();
//
//        String bookName = book.getBookName();
//        String author = book.getAuthor();
//        String publisher = book.getPublisher();
//        String kdc = book.getKdc();
//        String category = book.getCategory();
//        String keyword = book.getKeyword();
//        String img = book.getImg();
//
////        //내가 읽은 책의 카테고리 가져온다.
//
//        if (book.getKeyword() != null) {
//            List<Book> recomBookList = bookRepository.findAllByKeyword(category);
//            bookList.addAll(recomBookList);
//        }
//        if (category != null && bookList.size() < 20) {
//            //카테고리 같은 책들 가져오기
//            List<Book> recomBookList = bookRepository.findAllByCategory(category);
//            bookList.addAll(recomBookList);
//        }
//
//        try {
//            userReviewRepository.save(RecomUserReview.builder()
//                    .userNo(userNo)
//                    .isbn(isbn)
//                    .bookName(bookName)
//                    .author(author)
//                    .publisher(publisher)
//                    .kdc(kdc)
//                    .category(category)
//                    .keyword(keyword)
//                    .img(img)
//                    .build());
//        } catch (Exception e) {
//            log.info(":: updateUserReviewRecom err :: error is {} ", e);
//        }
//
//    }


    /**
     * 3. 유저가 좋아요 누른 책들 추천
     *
     * @author hyunho
     * @since 2021/07/11
     **/
    /**
     * 유저가 읽고싶다고 저장한 데이터를 가져온다
     */
    private RecomInfo userWantRecom(long userId) {
        List<BookDto> bookInfoList = new ArrayList<>();
        try {

            List<BookWantDto> bookWantPage = bookWantRepository.findLikeAllByUserInfo(userId, Preference.LIKE, PageRequest.of(0, 100))
                    .stream()
                    .map(a -> BookWantDto.builder()
                            .bookWant(a)
                            .build()).collect(Collectors.toList());

            //todo ????? 생각해보기 ㅡㅡ
            for (BookWantDto bwd : bookWantPage) {
                bookInfoList.add(bookService.findBook3Step(bwd.getIsbn()));
            }
        } catch (Exception e) {
            log.error(":: userWantRecom err :: error is {} ", e);

        }
        if (bookInfoList.size() == 0) return null;
        else return RecomInfo.builder()
                .bookInfoList(bookInfoList)
                .title("회원님이 읽고싶어한 책이에요")
                .build();
    }

    /**
     * todo 해야함
     * 4. 유저 정보별 (성별 / 나이) 추천
     * @author hyunho
     * @since 2021/07/11
     * */

    /**
     * 5. 유저가 리뷰한 전체 책 기반으로 추천
     *
     * @author hyunho
     * @since 2021/07/11
     **/

    private RecomInfo userCategoryRecom(long userId) {

        List<String> userPriorityList = userService.getUserPriorityList(userId);
        String userPriority;
        String userPriorityValue = "";
        List<BookDto> bestSeller = null;
        if(userPriorityList == null) return null;
        if (userPriorityList.size() >= 1) {
            userPriority = userPriorityList.get(0);
            userPriorityValue = SemoConstant.CATEGORY_TYPE_MAP.get(userPriority);

            bestSeller = bestSellerService.getBestSellerList(userPriority, 10).stream().map(a -> BookDto.builder()
                    .isbn(a.getIsbn())
                    .bookName(a.getBookName())
                    .author(a.getAuthor())
                    .publisher(a.getPublisher())
                    .category(a.getCategory())
                    .img(a.getImg())
                    .build()).collect(Collectors.toList());

        }
        if (bestSeller == null || bestSeller.size() == 0) return null;
        return RecomInfo.builder()
                .title("회원님이 선호하는 " + userPriorityValue + "분야의 책을 모아봤어요")
                .bookInfoList(bestSeller)
                .build();
    }

    /**
     * 6. 베스트셀러 추천
     *
     * @author hyunho
     * @since 2021/07/11
     **/
    private RecomInfo bestSellerRecom() {
        List<BookDto> bookInfoList = new ArrayList<>();
        try {
            bookInfoList = bestSellerService.getBestSellerList("A", 10).stream().map(a -> BookDto.builder()
                    .isbn(a.getIsbn())
                    .bookName(a.getBookName())
                    .author(a.getAuthor())
                    .publisher(a.getPublisher())
                    .category(a.getCategory())
                    .img(a.getImg())
                    .build()).collect(Collectors.toList());


        } catch (Exception e) {
            log.error(":: bestSellerRecom err :: error is {} ", e);

        }
        return RecomInfo.builder()
                .title("베스트 셀러")
                .bookInfoList(bookInfoList)
                .build();
    }
    /**
     * 7. 스테디셀러 추천
     *
     * @author hyunho
     * @since 2021/07/11
     **/

    private RecomInfo steadySellerRecom() {
        List<BookDto> bookInfoList = new ArrayList<>();
        try {
            bookInfoList = bestSellerService.getSteadySellerList("A", 10).stream().map(a -> BookDto.builder()
                    .isbn(a.getIsbn())
                    .bookName(a.getBookName())
                    .author(a.getAuthor())
                    .publisher(a.getPublisher())
                    .category(a.getCategory())
                    .img(a.getImg())
                    .build()).collect(Collectors.toList());


        } catch (Exception e) {
            log.error(":: bestSellerRecom err :: error is {} ", e);

        }
        return RecomInfo.builder()
                .title("스테디 셀러")
                .bookInfoList(bookInfoList)
                .build();
    }


    /**
     * isbn list로 책 정보 가져오기
     *
     * @author hyunho
     * @since 2021/07/11
     **/

    List<BookDto> getBookInfoByIsbnList(List<String> isbnList) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (String isbn : isbnList) {
            bookDtoList.add(bookService.findBook3Step(isbn));
        }
        return bookDtoList;
    }


}