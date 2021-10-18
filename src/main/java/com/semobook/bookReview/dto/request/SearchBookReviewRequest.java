package com.semobook.bookReview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchBookReviewRequest {
    @Schema(description = "isbn" , example = "9788935668663")
    String isbn;
    @Schema(description = "시작페이지, 시작페이지 기준으로 5개 나옴" , example = "0")
    int startPage;
}
