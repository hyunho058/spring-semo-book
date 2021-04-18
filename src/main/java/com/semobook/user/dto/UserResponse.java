package com.semobook.user.dto;

import com.semobook.common.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    @ApiModelProperty(value = "성공 코드" , example = "성공 : hd1004")
    StatusEnum hCode;
    @ApiModelProperty(value = "메시지")
    String hMessage;
    @ApiModelProperty(value = "데이터")
    Object data;

    @Builder
    public UserResponse(StatusEnum hcode, String hMessage, Object data) {
        this.hCode = hcode;
        this.hMessage = hMessage;
        this.data = data;
    }
}
