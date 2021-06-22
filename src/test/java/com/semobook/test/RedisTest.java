package com.semobook.test;


import com.semobook.recom.domain.UserPriorityRedis;
import com.semobook.recom.repository.RecomBestSellerRepository;
import com.semobook.recom.repository.UserPriorityRedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class RedisTest {

    @Autowired
    UserPriorityRedisRepository userPriorityRepository;
    @Autowired
    RecomBestSellerRepository bestSellerRepository;

    @Test
    @DisplayName("REDIS USER_PRIOROTY SAVE, GET TEST")
    void user_repository_test(){
        String value = "1_A:2_B:3_C:4_E";
        UserPriorityRedis up = UserPriorityRedis.builder().userNo(1).value(value).build();
        userPriorityRepository.save(up);

        userPriorityRepository.findById(up.getUserNo()).ifPresent(a-> assertEquals(value, a.getValue()));
    }

    @Test
    @DisplayName("REDIS USER_PRIOROTY GET TEST")
    void bestSeller_repository_test(){
        String value = "9788935668663";
        bestSellerRepository.findById("A_1").ifPresent(a-> {
            System.out.println(a.getIsbn());
            assertEquals(value, a.getIsbn());
        }
        );
    }

}
