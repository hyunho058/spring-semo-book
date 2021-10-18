package com.semobook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserReviewInfo {
    int bookThisMonth;
    int bookTotal;
    List<String> prioertiesList;

}
