package io.hexbit.core.user.dto;

import io.hexbit.core.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long userId;
    private String email;
    private String nickName;

    @Builder
    public UserResponseDto(Long userId, String email, String nickName) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
    }

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }
}
