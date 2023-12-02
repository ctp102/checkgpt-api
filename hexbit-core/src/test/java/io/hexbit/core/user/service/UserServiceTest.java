package io.hexbit.core.user.service;

import io.hexbit.core.common.exception.CustomNotFoundException;
import io.hexbit.core.oauth2.enums.OAuth2ProviderTypes;
import io.hexbit.core.user.domain.User;
import io.hexbit.core.user.dto.UserResponseDto;
import io.hexbit.core.user.repository.UserRepository;
import io.hexbit.core.user.repository.UserSearchForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("local")
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("[성공] 최초 카카오 로그인 시 강제 회원가입을 진행한다.")
    void joinUserSuccess() {
        // given
        Long providerId = ThreadLocalRandom.current().nextLong(1_000_000);
        String provider = OAuth2ProviderTypes.KAKAO.name().toLowerCase();
        String email = "jh.cho@abc.com";
        String nickname = "jh.cho";

        User user = User.of(providerId, provider, email, nickname);

        // when
        User savedUser = userService.joinUser(user);

        // then
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getProviderId()).isEqualTo(providerId);
        assertThat(savedUser.getProvider()).isEqualTo(provider);
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getNickName()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("[성공] 사용자 목록을 조회한다.")
    void getUserListSuccess() {
        // given
        Long providerId = ThreadLocalRandom.current().nextLong(1_000_000);
        String provider = OAuth2ProviderTypes.KAKAO.name().toLowerCase();
        String email = "jh.cho@abc.com";
        String nickname = "jh.cho";

        User user = User.of(providerId, provider, email, nickname);

        // when
        User savedUser = userService.joinUser(user);

        UserSearchForm userSearchForm = new UserSearchForm();
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<UserResponseDto> userList = userService.getUserList(userSearchForm, pageRequest);

        // then
        assertThat(userList.getTotalElements()).isEqualTo(1);
        assertThat(userList.getContent().get(0).getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(userList.getContent().get(0).getEmail()).isEqualTo(email);
        assertThat(userList.getContent().get(0).getNickName()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("[성공] 사용자 단건을 조회한다.")
    void getUserSuccess() {
        // given
        Long providerId = ThreadLocalRandom.current().nextLong(1_000_000);
        String provider = OAuth2ProviderTypes.KAKAO.name().toLowerCase();
        String email = "jh.cho@abc.com";
        String nickname = "jh.cho";

        User user = User.of(providerId, provider, email, nickname);

        // when
        User savedUser = userService.joinUser(user);
        User getUser = userService.getUser(savedUser.getUserId());

        // then
        assertThat(getUser.getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(getUser.getProviderId()).isEqualTo(providerId);
        assertThat(getUser.getProvider()).isEqualTo(provider);
        assertThat(getUser.getEmail()).isEqualTo(email);
        assertThat(getUser.getNickName()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("[실패] 사용자 단건을 조회한다. - 등록되지 않은 사용자")
    void getUserFailNotFound() {
        // given
        Long userId = ThreadLocalRandom.current().nextLong(1_000_000);

        // when
        // then
        assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(CustomNotFoundException.class);
    }

}