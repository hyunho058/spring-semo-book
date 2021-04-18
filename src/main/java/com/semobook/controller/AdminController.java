package com.semobook.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
@Api(tags = "추천 Controller" )
public class AdminController {


    @GetMapping(value = "/")
    public String adminrootpage() {
        return "admin root page";
    }



}
