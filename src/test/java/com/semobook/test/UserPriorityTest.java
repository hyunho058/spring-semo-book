package com.semobook.test;

import com.semobook.book.repository.RecomBestSellerRepository;
import com.semobook.bookReview.domain.AllReview;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.recom.domain.ReviewInfo;
import com.semobook.user.domain.UserPriorityRedis;
import com.semobook.user.repository.UserPriorityRedisRepository;
import com.semobook.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserPriorityTest {
    @Autowired
    UserPriorityRedisRepository userPriorityRepository;
    @Autowired
    RecomBestSellerRepository bestSellerRepository;
    @Autowired
    UserService userService;
    @Autowired
    AllReviewRepository allReviewRepository;
    @Autowired
    UserPriorityRedisRepository userPriorityRedisRepository;


    @Test
    @DisplayName("유저성향을_가져온다")
    void user_repository_test() {

        //given
        UserPriorityRedis userPriorityRedis =
                UserPriorityRedis.builder()
                        .userNo(9999)
                        .value("700:200:400:800")
                        .build();
        userPriorityRepository.save(userPriorityRedis);
        //when
        List<String> userPriority = userService.getUserPriorityList(9999);
        List<String> userPriorityFalse = userService.getUserPriorityList(8888);
        List<String> resutChk = Arrays.asList(new String[]{"700", "200", "400", "800"});
        //then
        assertTrue(Arrays.equals(userPriority.toArray(), resutChk.toArray()));
        assertNull(userPriorityFalse);
        //final
        userPriorityRepository.delete(userPriorityRedis);

    }

    @Test
    @DisplayName("REDIS_DATA로_유저성향을_만든다")
    void createPriorityUsingRedisData() {
        List<ReviewInfo> infoList = new ArrayList<>();
        //given
        infoList.add(ReviewInfo.builder()
                .isbn("9788956604992")
                .category("800")
                .point(5)
                .build());
        infoList.add(ReviewInfo.builder()
                .isbn("9788998274931")
                .category("800")
                .point(3)
                .build());
        infoList.add(ReviewInfo.builder()
                .isbn("9788937417566")
                .category("800")
                .point(4).build());
        infoList.add(ReviewInfo.builder()
                .isbn("9788934972464")
                .category("300")
                .point(3)
                .build());
        AllReview review = AllReview.builder()
                .userId(99999)
                .value(infoList)
                .build();
        allReviewRepository.save(review);
        //when
        boolean chkTrue = userService.makeUserPriority(99999);
        boolean chkFalse = userService.makeUserPriority(88888);
        List<String> priorityList = userService.getUserPriorityList(99999);
        List<String> resutChk = Arrays.asList(new String[]{"800", "300"});
        //then
        assertTrue(chkTrue);
        assertFalse(chkFalse);
        assertTrue(Arrays.equals(priorityList.toArray(), resutChk.toArray()));

        //final
//        userPriorityRedisRepository.delete(UserPriorityRedis.builder().userNo(99999).build());
//        allReviewRepository.delete(review);

    }

    @Test
    @DisplayName("DB_DATA로_유저성향을_만든다")
    void createPriorityUsingDBData() {
        //given
        //when
        //then
        //redis에 리뷰 값 저장된것도 확인하기
        //final
    }
}
