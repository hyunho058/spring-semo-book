package com.semobook.bookwant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BookWantCreateRequest {
    @Schema(description = "유저no" , example = "12")
    long userNo;
    @Schema(description = "책isbn" , example = "9788998139766")
    String isbn;
    @Schema(description = "성향 : LIKE/DISLIKE" , example = "LIKE")
    Preference preference;

}
