package io.hexbit.core.user.repository;

import io.hexbit.core.user.domain.UserSessionDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionDeviceRepository extends JpaRepository<UserSessionDevice, String> {
}
