package com.semobook.accessterms.service;


import com.semobook.accessterms.domain.Accessterms;
import com.semobook.accessterms.dto.AccessTermsDto;
import com.semobook.accessterms.dto.AccessTermsResponse;
import com.semobook.accessterms.repository.AccessTermsRepository;
import com.semobook.common.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessTermsService {
    private final AccessTermsRepository accesstermsRepository;

    private String hMessage = null;
    private Object data = null;
    private StatusEnum hCode = null;
    /**
     * AccesstrtmsService(전체 데이터 검색)
     *
     * @author juhan
     * @since 2021-07-17
    **/
    public AccessTermsResponse findAll(long accessNo){
        log.info(":: AccessTermsService_findData :: accessNo : {}" ,accessNo);
        hMessage = null;
        data = null;
        hCode = null;

        try{
            Accessterms accessterms = accesstermsRepository.findByAccessNo(accessNo);
            log.info("AccessTerms : {}", accessterms);
            AccessTermsDto accessTermsDto = new AccessTermsDto(accessterms);
            log.info("AccessTerms : {}", accessTermsDto.getAccessType());

            hCode = StatusEnum.hd1004;
            hMessage = "저장완료";
            data = accessTermsDto;

        }catch (Exception e){
            hCode = StatusEnum.hd4444;
            hMessage = "저장실패";
            data = null;
        }
        return AccessTermsResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }
}
