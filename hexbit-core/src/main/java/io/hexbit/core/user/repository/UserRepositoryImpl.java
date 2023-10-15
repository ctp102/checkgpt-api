package io.hexbit.core.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hexbit.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static io.hexbit.core.user.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<User> findAll(UserSearchForm userSearchForm, Pageable pageable) {

        List<User> members = queryFactory
                .selectFrom(user)
                .where(
                        likeEmail(userSearchForm.getEmail()),
                        likeNickName(userSearchForm.getNickName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // TODO: Deprecated 된 메서드 사용하지 않기
        long totalCount = queryFactory
                .selectFrom(user)
                .where(
                        likeEmail(userSearchForm.getEmail()),
                        likeNickName(userSearchForm.getNickName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(members, pageable, totalCount);
    }

    public BooleanExpression likeEmail(String email) {
        return StringUtils.isNotBlank(email) ? user.email.like(email + "%") : null;
    }

    public BooleanExpression likeNickName(String name) {
        return StringUtils.isNotBlank(name) ? user.nickName.like(name + "%") : null;
    }

}
