package io.hexbit.api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "Home API")
@Controller
public class HomeController {

    @Hidden
    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @Hidden
    @GetMapping("/v1")
    @ResponseBody
    public String v1() {
        return "Hello v1";
    }

    @Operation(summary = "헬스 체크", description = "서버가 정상적으로 실행 중인지 확인합니다.")
    @GetMapping("/health-check")
    @ResponseBody
    public String healthCheck() {
        return "OK";
    }

}
