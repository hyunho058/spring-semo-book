package com.semobook.user.dto;

import com.semobook.common.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public UserResponse(StatusEnum hCode, String hMessage, Object data) {
        this.hCode = hCode;
        this.hMessage = hMessage;
        this.data = data;
    }
}
