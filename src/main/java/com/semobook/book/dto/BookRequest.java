package com.semobook.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookRequest {
    @Schema(description = "책 고유번호" , example = "111111")
    String isbn;
    @Schema(description = "첵 이름", example = "세모책")
    String bookName;
    @Schema(description = "저자",example = "세모")
    String author;
    @Schema(description = "출판사",example = "hDream")
    String publisher;
    @Schema(description = "KDC",example = "kdc11111")
    String kdc;
    @Schema(description = "책 분류",example = "소설")
    String categoryy;
    @Schema(description = "책 키워드",example = "청소년 소설")
    String keyword;
    @Schema(description = "책 이미지",example = "http://image.kyobobook.co.kr/images/book/xlarge/267/x9788936434267.jpg")
    String img;
}
