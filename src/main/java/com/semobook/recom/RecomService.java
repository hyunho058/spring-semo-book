package com.semobook.recom;

public class RecomService {

    //1순위 : 유저가 리뷰를 쓰면 -> 리뷰책과 관련된 장르의 책 추천 REDIS KEY : USER_REVIEW_RECOM
            //1. 이책을 본 사람들의 다른 책들 추천
            //2. 이책과 비슷한 장르 추천
    //2순위 : 유저가 종합적으로 준 평점 기반 추천 : USER_TOTAL_RECOM
    //3순위 : 유저 정보 (나이, 성별) 기반 추천 : USER_INFO_RECOM
    //4순위 : 베스트 셀러 추천 : BSETSELLER_RECOM
}
