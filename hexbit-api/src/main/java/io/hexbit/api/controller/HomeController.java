package io.hexbit.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/v1")
    @ResponseBody
    public String v1() {
        return "Hello v1";
    }

    @GetMapping("/health-check")
    @ResponseBody
    public String healthCheck() {
        return "OK";
    }

}
