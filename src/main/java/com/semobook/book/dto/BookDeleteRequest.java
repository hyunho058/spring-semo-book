package com.semobook.book.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookDeleteRequest {
    @Schema(description = "ISBN", example = "11111111")
    long isbn;
}
