package com.example.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/v1")
    public String v1() {
        return "Hello v1";
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

}
