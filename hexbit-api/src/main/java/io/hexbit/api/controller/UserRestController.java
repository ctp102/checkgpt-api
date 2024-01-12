package io.hexbit.api.controller;

import io.hexbit.api.config.annotation.Swagger200Response;
import io.hexbit.api.security.annotation.ClientRequest;
import io.hexbit.api.security.annotation.Secured;
import io.hexbit.api.security.resolver.WebRequest;
import io.hexbit.core.common.exception.CustomUnauthorizedException;
import io.hexbit.core.common.response.CustomResponse;
import io.hexbit.core.user.domain.User;
import io.hexbit.core.user.dto.UserResponseDto;
import io.hexbit.core.user.repository.UserSearchForm;
import io.hexbit.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "회원 목록 조회", description = "회원 가입된 회원 목록을 조회한다.")
    @Parameters({
            @Parameter(name = "webRequest", hidden = true),
            @Parameter(name = "userSearchForm", description = "회원 검색 폼", required = true),
            @Parameter(name = "pageable", hidden = true)
    })
    @Swagger200Response
    @Secured
    @GetMapping("/api/v1/users")
    public CustomResponse getUserList(
            @ClientRequest WebRequest webRequest,
            @ModelAttribute UserSearchForm userSearchForm,
            @PageableDefault Pageable pageable) {

        Page<UserResponseDto> items = userService.getUserList(userSearchForm, pageable);

        return new CustomResponse.Builder().addItems(items).build();
    }

    @Operation(summary = "회원 정보 단건 조회", description = "회원 가입된 회원 정보 단건을 조회한다.")
    @Parameters({
            @Parameter(name = "webRequest", hidden = true),
            @Parameter(name = "userId", description = "회원 ID", required = true, example = "1")
    })
    @Swagger200Response
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
