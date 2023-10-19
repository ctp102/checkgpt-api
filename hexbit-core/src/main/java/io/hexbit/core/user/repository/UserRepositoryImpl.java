package io.hexbit.core.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hexbit.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(
                        likeEmail(userSearchForm.getEmail()),
                        likeNickName(userSearchForm.getNickName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(members, pageable, countQuery::fetchCount);
    }

    public BooleanExpression likeEmail(String email) {
        return StringUtils.isNotBlank(email) ? user.email.like(email + "%") : null;
    }

    public BooleanExpression likeNickName(String name) {
        return StringUtils.isNotBlank(name) ? user.nickName.like(name + "%") : null;
    }

}
