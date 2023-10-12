package io.hexbit.core.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hexbit.core.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {

    private Long userId;
    private String email;
    private String nickName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDt;

    @Builder
    public UserResponseDto(Long userId, String email, String nickName, LocalDateTime createdDt) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.createdDt = createdDt;
    }

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .createdDt(user.getCreatedDt())
                .build();
    }
}
