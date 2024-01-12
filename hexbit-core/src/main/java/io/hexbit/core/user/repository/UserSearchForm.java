package io.hexbit.core.user.repository;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSearchForm {

    @Schema(description = "회원 ID", example = "1")
    private String email;

    @Schema(description = "회원 이름", example = "홍길동")
    private String nickName;

}
