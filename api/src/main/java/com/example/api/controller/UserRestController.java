package com.example.api.controller;

import com.example.core.common.response.CustomResponse;
import com.example.core.user.domain.User;
import com.example.core.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/v1/users/{userId}")
    public CustomResponse getUser(@PathVariable Long userId, HttpServletRequest request) {
        User item = userService.getUser(userId);

        return new CustomResponse.Builder().addItems(item).build();
    }

}
