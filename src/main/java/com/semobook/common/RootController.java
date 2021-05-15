package com.semobook.common;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
@Controller
@RequestMapping(value="/")
public class RootController {
    @RequestMapping("/")
    public String homeRedirect() {
        return "redirect:/swagger-ui.html";
    }
}
//}