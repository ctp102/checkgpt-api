package io.hexbit.core.user.domain;

import io.hexbit.core.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USERS_PROVIDER_ID", columnNames = {"PROVIDER_ID", "PROVIDER"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PROVIDER_ID", nullable = false)
    private Long providerId;

    @Column(name = "PROVIDER", nullable = false)
    private String provider;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NICK_NAME")
    private String nickName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @BatchSize(size = 500)
    List<UserSessionDevice> userSessionDevices;

    @Builder
    public User(Long userId, Long providerId, String provider, String email, String nickName) {
        this.userId = userId;
        this.providerId = providerId;
        this.provider = provider;
        this.email = email;
        this.nickName = nickName;
    }

    public static User of(Long providerId, String provider, String email, String nickName) {
        return User.builder()
                .providerId(providerId)
                .provider(provider)
                .email(email)
                .nickName(nickName)
                .build();
    }

    public void addSessionDevice(UserSessionDevice userSessionDevice) {
        this.userSessionDevices.add(userSessionDevice);
        userSessionDevice.addUser(this);
    }

}
