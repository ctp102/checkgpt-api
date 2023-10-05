package io.hexbit.api.controller;

import io.hexbit.api.security.annotation.ClientRequest;
import io.hexbit.api.security.annotation.Secured;
import io.hexbit.api.security.resolver.WebRequest;
import io.hexbit.core.common.response.CustomResponse;
import io.hexbit.core.user.domain.User;
import io.hexbit.core.user.dto.UserResponseDto;
import io.hexbit.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Secured
    @GetMapping("/api/v1/users")
    public CustomResponse getUserList(@ClientRequest WebRequest webRequest, @PageableDefault Pageable pageable) {
        Page<UserResponseDto> items = userService.getUserList(pageable);

        return new CustomResponse.Builder().addItems(items).build();
    }

    @Secured
    @GetMapping("/api/v1/users/{userId}")
    public CustomResponse getUser(@ClientRequest WebRequest webRequest, @PathVariable Long userId) {
        User item = userService.getUser(userId);

        return new CustomResponse.Builder().addItems(item).build();
    }

}
