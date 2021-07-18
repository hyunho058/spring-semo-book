package com.semobook.user.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;


/**
 * DB에 있는 user_priority 값을 캐싱한다.
 * USER_PRIORITY:12
 * value 1_A:2_B:3_C:4_D...
 *
 * @author hyejinzz
 * @since 2021-06-19
 **/
@Data
@NoArgsConstructor
@RedisHash("USER_PRIORITY_REDIS")
public class UserPriorityRedis implements Serializable {
    @Id
    long userNo;
    String value;

    @Builder
    public UserPriorityRedis(long userNo, String value) {

        this.userNo = userNo;
        this.value = value;
    }


}
