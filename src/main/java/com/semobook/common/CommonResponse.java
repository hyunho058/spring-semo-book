package com.semobook.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommonResponse {

    @Data
    @NoArgsConstructor
    public class UserResponse {

        @Schema(description = "성공 코드" , example = "성공 : hd1004")
        StatusEnum hCode;
        @Schema(description = "메시지")
        String hMessage;
        @Schema(description = "데이터")
        Object data;

        @Builder
        public UserResponse(StatusEnum hcode, String hMessage, Object data) {
            this.hCode = hcode;
            this.hMessage = hMessage;
            this.data = data;
        }
    }


}
