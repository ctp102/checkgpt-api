package io.hexbit.core.user.domain;

import io.hexbit.core.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private Long providerId;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String email;

    @Column
    private String nickName;

    @Builder
    private User (Long providerId, String provider, String email, String nickName) {
        this.providerId = providerId;
        this.provider = provider;
        this.email = email;
        this.nickName = nickName;
    }

    public static User toEntity(Long providerId, String provider, String email, String nickName) {
        return User.builder()
                .providerId(providerId)
                .provider(provider)
                .email(email)
                .nickName(nickName)
                .build();
    }

}
