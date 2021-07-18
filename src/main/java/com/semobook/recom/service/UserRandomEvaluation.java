package com.semobook.recom.service;

import com.semobook.book.dto.BookDto;
import com.semobook.book.service.BestSellerService;
import com.semobook.bookReview.domain.AllReview;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.common.SemoConstant;
import com.semobook.common.StatusEnum;
import com.semobook.recom.domain.ReviewInfo;
import com.semobook.recom.dto.RecomResponse;
import com.semobook.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRandomEvaluation {

    private final UserService userService;
    private final AllReviewRepository allReviewRepository;
    private final BestSellerService bestSellerService;
    private final ResultFilterService resultFilterService;
    Map<String, Integer> categoryIndex;


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
        List<BookDto> recomBestSellersList = new ArrayList<>();
        Object data = null;
        StatusEnum hCode = null;
        String hMessage = null;
        try {
            hCode = StatusEnum.hd1004;
            userPriority = userService.getUserPriorityList(userId);

            if (userPriority.size() == 0) {
                recomBestSellersList = basicEvaluation();
            }
            if (userPriority.size() > 0) {
                // 성향별로 가져오기
                recomBestSellersList = userEvaluation(userPriority);
            }
            if (recomBestSellersList.size() > 0 && recomBestSellersList.get(0) == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "userRandomEvaluation fail";
            }
            if (recomBestSellersList.size() > 0 && recomBestSellersList.get(0) != null) {
                data = recomBestSellersList;
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
    private List<BookDto> userEvaluation(List<String> userPriority) {
        Map<String, Integer> goalMap = new HashMap<>();
        List<BookDto> list = new ArrayList<>();
        for (int i = 0; i < userPriority.size(); i++) {
            switch (i + 1) {
                case 1:
                    goalMap.put(userPriority.get(i), SemoConstant.FIRST_PRIORITY_RATIO);
                    break;
                case 2:
                    goalMap.put(userPriority.get(i), SemoConstant.SECOND_PRIORITY_RATIO);
                    break;
                case 3:
                    goalMap.put(userPriority.get(i), SemoConstant.THRID_PRIORITY_RATIO);
                    break;
                case 4:
                    goalMap.put(userPriority.get(i), SemoConstant.FIRTH_PRIORITY_RATIO);
                    break;
                case 5:
                    goalMap.put(userPriority.get(i), SemoConstant.FIFTH_PRIORITY_RATIO);
                    break;
            }
        }
        for (String s : goalMap.keySet()) {
            list.addAll(bestSellerService.getBestSellerSteadySellerListMix(s, goalMap.get(s)));
            list.addAll(bestSellerService.getBestSellerSteadySellerListMix(s, goalMap.get(s)));
        }

        //부족한 개수 채우기
        if (list.size() < 20) {
            int searchSize = 20 - list.size();
            list.addAll(bestSellerService.getBestSellerSteadySellerListMix("A", searchSize));
        }

        return list;
    }

    /**
     * 유저 정보가 없을경우 BestSeller 분야별 (종합제외) 로 1개씩 가져오기
     *
     * @return
     */
    private List<BookDto> basicEvaluation() {
        List<BookDto> bookList = new ArrayList<>();
        for (String s : SemoConstant.CATEGORY_TYPE) {
            BookDto ss = bestSellerService.getSteadySeller(s);
            if(ss.getIsbn() != null){
                bookList.add(ss);
            }
            BookDto bs = bestSellerService.getSteadySeller(s);
            if(bs.getIsbn() != null){
                bookList.add(bs);
            }

        }
        bookList = resultFilterService.BookDtoListCutter(bookList);
        return bookList;
    }


    /**
     * 리뷰쓰면 redis에 update
     * redis Date Update
     */
    private void updateDataFromRedis(long userId, ReviewInfo value) {
        AllReview find = allReviewRepository.findById(userId).orElse(null);
        List<ReviewInfo> findValue = new ArrayList<>();
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


}