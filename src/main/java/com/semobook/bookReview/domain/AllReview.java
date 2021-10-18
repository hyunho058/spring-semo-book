package com.semobook.bookReview.domain;

import com.semobook.recom.domain.ReviewInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
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
