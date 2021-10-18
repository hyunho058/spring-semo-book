package com.semobook.common;

import lombok.Getter;
//TODO[refactoring] : hCOde 세분화
@Getter
public enum StatusEnum {
    hd1004, //success
    hd4444, //non err
    hd400 //empty data

    /**
     * todo 추가해야 할 Status
     * 아이디 틀림
     * 데이터 없음
     * 유효하지 않은 리퀘스트
     *
     */

}
