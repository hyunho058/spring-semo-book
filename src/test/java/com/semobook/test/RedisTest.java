package com.semobook.test;


import com.semobook.book.domain.RecomBestSeller;
import com.semobook.common.SemoConstant;
import com.semobook.bookReview.domain.AllReview;
import com.semobook.recom.domain.ReviewInfo;
import com.semobook.user.dto.UserPriorityRedis;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.book.repository.RecomBestSellerRepository;
import com.semobook.user.repository.UserPriorityRedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class RedisTest {

    @Autowired
    UserPriorityRedisRepository userPriorityRepository;
    @Autowired
    RecomBestSellerRepository recomBestSellerRepository;
    @Autowired
    AllReviewRepository allReviewRepository;


    @Test
    @DisplayName("REDIS USER_PRIOROTY SAVE, GET TEST")
    void user_repository_test() {
        String value = "1_A:2_B:3_C:4_E";
        UserPriorityRedis up = UserPriorityRedis.builder().userNo(1).value(value).build();
        userPriorityRepository.save(up);

        userPriorityRepository.findById(up.getUserNo()).ifPresent(a -> assertEquals(value, a.getValue()));
    }

    @Test
    @DisplayName("REDIS USER_PRIOROTY GET TEST")
    void bestSeller_repository_test() {
        String value = "9788935668663";
        recomBestSellerRepository.findById("A_1").ifPresent(a -> {
                    System.out.println(a.getIsbn());
                    assertEquals(value, a.getIsbn());
                }
        );
    }


    @Test
    @DisplayName("REDIS ALL_REVIEW SAVE, GET TEST")
    void all_review_test() {
        List<ReviewInfo> infoList = new ArrayList<>();
        ReviewInfo info = new ReviewInfo("124","b",3);
        infoList.add(ReviewInfo.builder()
                .isbn("124")
                .category("B_")
                .point(3)
                .build());

        infoList.add(ReviewInfo.builder()
                .isbn("133")
                .category("C_")
                .point(3)
                .build());

        infoList.add(ReviewInfo.builder()
                .isbn("134")
                .category("C_")
                .point(2)
                .build());

        AllReview allReview = AllReview.builder()
                .userId(5)
                .value(infoList)
                .build();
        allReviewRepository.save(allReview);

        allReviewRepository.findById(5L)
                .ifPresent(a-> a.getValue().stream()
                        .forEach(b-> System.out.println(b.getIsbn())));

    }

    @Test
    @DisplayName("REDIS ALL_REVIEW_CONVERT_TO_MAP")
    void converter_test() {
        String value = "124:B_|133:C_|134:C_";
        Map<String,String> map = new HashMap<>();


        String tempArr[] =  value.split(SemoConstant.BACKSLASH_VARTICAL_BAR);
        for(String s : tempArr){
            System.out.println(s);
            String [] array = s.split(SemoConstant.COLON);
            map.put(array[0],array[1]);
        }
        assertEquals(map.get("124"),"B_");
        assertEquals(map.get("133"),"C_");
        assertEquals(map.get("134"),"C_");
    }

    @Test
    @DisplayName("REDIS_GET_BEST_SELLER")
    void bestseller_test(){
        RecomBestSeller a_2 = recomBestSellerRepository.findById("A_1").orElse(null);
        System.out.println(a_2.getBookName());

    }

}
