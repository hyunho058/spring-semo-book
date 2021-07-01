package com.semobook.bookReview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MonthBookReviewRequest {
    @Schema(description = "유저번호" , example = "1")
    long userNo;
    @Schema(description = "start date" , example = "2021-06-01T00:00")
    LocalDateTime startDate;
    @Schema(description = "end date" , example = "2021-06-30T23:59:59")
    LocalDateTime endDate;
}
