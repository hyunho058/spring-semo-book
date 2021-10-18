package com.semobook.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailRequest {

    private String address;
    private String title;
    private String message;
}
