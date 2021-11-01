package com.semobook.qa.service;

import com.semobook.common.StatusEnum;
import com.semobook.qa.domain.Qa;
import com.semobook.qa.dto.QaListDto;
import com.semobook.qa.dto.QaRequest;
import com.semobook.qa.dto.QaResponse;
import com.semobook.qa.repository.QaRepository;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QaService {
    private final QaRepository qaRepository;
    private final UserRepository userRepository;
    private String hMessage = null;
    private StatusEnum hCode = null;

    /**
     * create qa
     *
     * @author hyunho
     * @since 2021-07-10
     **/
    @Transactional
    public QaResponse createQa(QaRequest request) {
        log.info(":: createQa ::");
        hMessage = null;
        hCode = null;
        Qa qa = null;
        log.info(":: createQa :: request is {} ", request);
        try {
            UserInfo resultUserInfo = userRepository.findByUserNo(request.getUserNo());
            qa = qaRepository.save(Qa.builder()
                    .title(request.getTitle())
                    .requestContents(request.getRequestContents())
                    .userInfo(resultUserInfo)
                    .createDate(LocalDateTime.now())
                    .build());

            hCode = StatusEnum.hd1004;
            hMessage = "저장완료";
        }catch (Exception e){
            hCode = StatusEnum.hd4444;
            hMessage = "저장실패";
        }

        return QaResponse.builder()
                .data(qa.getQaNo())
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    /**
     * create qa
     *
     * @author hyunho
     * @since 2021-07-10
     **/
    public QaResponse findAllQa(long userNo, int pageNum){
        log.info(":: findAllQa ::");
        hMessage = null;
        hCode = null;
        List<QaListDto> result = null;
        try {
            Page<Qa> page = qaRepository.qaList(userNo,
                    PageRequest.of(pageNum, 10));
            result = page.getContent().stream()
                    .map(qa -> new QaListDto(qa))
                    .collect(Collectors.toList());

            hCode = StatusEnum.hd1004;
            hMessage = "qa조회 성공";
        }catch (Exception e){
            hCode = StatusEnum.hd4444;
            hMessage = "qa조회 실패";
        }

        return QaResponse.builder()
                .data(result)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }
}
