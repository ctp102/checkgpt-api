package io.hexbit.core.user.repository;

import io.hexbit.core.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {

    Page<User> findAll(UserSearchForm userSearchForm, Pageable pageable);

}
