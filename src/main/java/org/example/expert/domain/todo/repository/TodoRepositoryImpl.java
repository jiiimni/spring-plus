package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        Todo result = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}

/**
 * 수정: 기존 @Query JPQL 대신 QueryDSL로 Todo 단건 조회 구현
 * 개념 정리:
 * 1) selectFrom(todo) -> Todo 엔티티 기준 조회
 * 2) leftJoin(todo.user, user).fetchJoin() -> 연관된 user를 한 번의 쿼리로 함께 조회
 * 3) where(todo.id.eq(todoId)) -> todoId와 일치하는 Todo만 조회
 * 4) fetchOne() -> 결과가 1건일 것으로 기대되는 단건 조회
 */