package com.semobook.accessterms.dto;

import com.semobook.accessterms.domain.Accessterms;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AccessTermsDto {

    private long accessNo;

    private String accessType;

    private String accessContents;

    public AccessTermsDto(Accessterms accessTerms){
        this.accessNo = accessTerms.getAccessNo();
        this.accessType = accessTerms.getAccessType();
        this.accessContents = accessTerms.getAccessContents();
    }
}
