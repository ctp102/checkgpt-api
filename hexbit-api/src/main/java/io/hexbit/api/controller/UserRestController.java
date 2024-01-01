package io.hexbit.api.controller;

import io.hexbit.api.security.annotation.ClientRequest;
import io.hexbit.api.security.annotation.Secured;
import io.hexbit.api.security.resolver.WebRequest;
import io.hexbit.core.common.exception.CustomUnauthorizedException;
import io.hexbit.core.common.response.CustomResponse;
import io.hexbit.core.user.domain.User;
import io.hexbit.core.user.dto.UserResponseDto;
import io.hexbit.core.user.repository.UserSearchForm;
import io.hexbit.core.user.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static io.hexbit.core.common.enums.ErrorCodes.SESSION_USER_NOT_MATCHED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Secured
    @GetMapping("/api/v1/users")
    public CustomResponse getUserList(
            @ClientRequest WebRequest webRequest,
            @ModelAttribute UserSearchForm userSearchForm,
            @PageableDefault Pageable pageable) {

        Page<UserResponseDto> items = userService.getUserList(userSearchForm, pageable);

        return new CustomResponse.Builder().addItems(items).build();
    }

    @Secured
    @GetMapping("/api/v1/users/{userId}")
    public CustomResponse getUser(
            @ClientRequest WebRequest webRequest,
            @PathVariable @Min(1) Long userId) {

        if (!webRequest.getUserId().equals(userId)) {
            throw new CustomUnauthorizedException(SESSION_USER_NOT_MATCHED.getNumber(), SESSION_USER_NOT_MATCHED.getMessage());
        }

        User item = userService.getUser(userId);

        return new CustomResponse.Builder().addItems(item).build();
    }

}
