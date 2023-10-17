package io.hexbit.api.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public TestResponse testOnGet(String params) {
        return new TestResponse(params);
    }

    @Data
    private static class TestResponse {
        private final String message;
    }

}