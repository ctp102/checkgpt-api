package com.example.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

//    @GetMapping("/v1")
//    public String v1() {
//        return "Hello v1";
//    }
//
//    @GetMapping("/health-check")
//    public String healthCheck() {
//        return "OK";
//    }

}
