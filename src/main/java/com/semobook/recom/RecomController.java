package com.semobook.recom;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recom")
@Api(tags = "추천 Controller" )
public class RecomController {
    @GetMapping(value = "/")
    public String recomrootpage() {
        return "recom root page";
    }

    //유저가 읽은 책 기반 추천
    @ApiOperation(value = "유저가 읽은 책 기반의 추천")
    @PostMapping("/readbook-based-recom")
    public String readbookBasedRecom(){
        return "";
    }

    //유저 정보 기반 추천
    @ApiOperation(value = "유저 정보(성별,나이) 기반의 추천")
    @PostMapping("/userinfo-based-recom")
    public String userInfoBasedRecom(){
        return "";
    }

    //베스트 셀러
    @ApiOperation(value = "베스트 셀러 추천")
    @PostMapping("/bestseller-recom")
    public String bestsellerRecom(){
        return "";
    }


}
