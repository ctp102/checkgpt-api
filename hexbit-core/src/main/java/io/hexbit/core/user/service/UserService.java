package io.hexbit.core.user.service;

import io.hexbit.core.common.exception.CustomNotFoundException;
import io.hexbit.core.user.domain.User;
import io.hexbit.core.user.repository.UserRepository;
import io.hexbit.core.common.enums.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void joinUser(User user) {

        User findUser = userRepository.findByProviderIdAndProvider(user.getProviderId(), user.getProvider());

        if (findUser != null) {
            log.info("이미 등록된 회원입니다. provider: {}, email: {}", user.getProvider(), user.getEmail());
            return;
        }

        User savedUser = userRepository.save(user);
        log.info("회원가입이 완료되었습니다. provider: {}, email: {}", savedUser.getProvider(), savedUser.getEmail());
    }

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            log.info("등록되지 않은 회원입니다. userId: {}", userId);
            throw new CustomNotFoundException(ErrorCodes.NOT_EXISTS_USER.getNumber(), ErrorCodes.NOT_EXISTS_USER.getMessage());
        }
        return user;
    }

}
