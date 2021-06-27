package com.semobook.recom.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class UserPriority {

    @Id
    private long userId;

    private String firstPriority;
    private String secondPriority;
    private String thirdPriority;
    private String fourthPriority;
    private String fifthPriority;

}
