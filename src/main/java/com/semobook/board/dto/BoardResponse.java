package com.semobook.board.dto;

import com.semobook.common.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponse {
    @Schema(description = "성공 코드" , example = "성공 : hd1004")
    StatusEnum hCode;
    @Schema(description = "메시지")
    String hMessage;
    @Schema(description = "데이터")
    Object data;

    @Builder
    public BoardResponse(StatusEnum hCode, String hMessage, Object data) {
        this.hCode = hCode;
        this.hMessage = hMessage;
        this.data = data;
    }


}
