package io.hexbit.core.user.dto;

import io.hexbit.core.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long userId;
    private Long providerId;
    private String provider;
    private String email;
    private String nickName;

    @Builder
    public UserResponseDto(Long userId, Long providerId, String provider, String email, String nickName) {
        this.userId = userId;
        this.providerId = providerId;
        this.provider = provider;
        this.email = email;
        this.nickName = nickName;
    }

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .providerId(user.getProviderId())
                .provider(user.getProvider())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }
}
