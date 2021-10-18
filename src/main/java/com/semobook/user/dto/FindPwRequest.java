package com.semobook.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindPwRequest {
    @Schema(description = "userId" , example = "semo00@semo.com")
    private String userId;
}
