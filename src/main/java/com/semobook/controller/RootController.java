package com.semobook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {


    @GetMapping(value = "/")
    public String rootPage() {
        return "http://localhost:8080/swagger-ui.html#/UserController";
    }
}
