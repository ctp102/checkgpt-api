package io.hexbit.core.user.repository;

import io.hexbit.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    User findByProviderIdAndProvider(Long providerId, String provider);

}
