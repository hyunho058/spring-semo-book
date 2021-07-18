package com.semobook.accessterms.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Accessterms {
    /**
     * 이용약관, 서비스관리
     *
     * @author juhan
     * @since 2021-07-17
    **/

    @Id
    @GeneratedValue
    private long accessNo;

    private String accessType;

    private String accessContents;

    @Builder
    public Accessterms(long accessNo, String accessType, String accessContents){
        this.accessNo = accessNo;
        this.accessType = accessType;
        this.accessContents = accessContents;
    }
}
