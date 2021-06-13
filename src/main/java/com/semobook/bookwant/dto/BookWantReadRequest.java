package com.semobook.bookwant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BookWantReadRequest {
    @Schema(description = "유저no" , example = "20")
    long userNo;
}
