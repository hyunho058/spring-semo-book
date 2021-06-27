package com.semobook.recom.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisKeyValueTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@RedisHash("ALL_REVIEW")
public class AllReview implements Serializable {
    @Id
    private long userId;
    //isbn:category:regDate|isbn:category:regDate
    List<ReviewInfo> value;


    @Builder
    public AllReview(long userId, List<ReviewInfo> value) {
        this.userId = userId;
        this.value = value;

    }
}
