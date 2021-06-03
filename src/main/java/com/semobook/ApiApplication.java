package com.semobook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

//    @Bean
//    Hibernate5Module hibernate5Module(){
//        Hibernate5Module hibernate5Module = new Hibernate5Module();
//        return hibernate5Module;
//    }
}
