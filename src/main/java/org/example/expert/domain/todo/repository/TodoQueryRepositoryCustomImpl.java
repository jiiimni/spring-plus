package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TodoQueryRepositoryCustomImpl implements TodoQueryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<TodoSearchResponse> searchTodos(TodoSearchRequest request, Pageable pageable) {
        QTodo todo = QTodo.todo;
        QManager manager = QManager.manager;
        QUser managerUser = new QUser("managerUser");
        QComment comment = QComment.comment;

        List<TodoSearchResponse> contents = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        manager.id.countDistinct(),
                        comment.id.countDistinct()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, managerUser)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContains(request.getTitle()),
                        managerNicknameContains(request.getManagerNickname()),
                        createdAtGoe(request.getCreatedAtFrom()),
                        createdAtLoe(request.getCreatedAtTo())
                )
                .groupBy(todo.id, todo.title, todo.createdAt)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> totalIds = queryFactory
                .select(todo.id)
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, managerUser)
                .where(
                        titleContains(request.getTitle()),
                        managerNicknameContains(request.getManagerNickname()),
                        createdAtGoe(request.getCreatedAtFrom()),
                        createdAtLoe(request.getCreatedAtTo())
                )
                .groupBy(todo.id)
                .fetch();

        long total = totalIds.size();

        return new PageImpl<>(contents, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return (title == null || title.isBlank()) ? null : QTodo.todo.title.contains(title);
    }

    private BooleanExpression managerNicknameContains(String managerNickname) {
        QManager manager = QManager.manager;
        QUser managerUser = new QUser("managerUser");
        return (managerNickname == null || managerNickname.isBlank())
                ? null
                : manager.user.nickname.contains(managerNickname);
    }

    private BooleanExpression createdAtGoe(LocalDateTime createdAtFrom) {
        return createdAtFrom == null ? null : QTodo.todo.createdAt.goe(createdAtFrom);
    }

    private BooleanExpression createdAtLoe(LocalDateTime createdAtTo) {
        return createdAtTo == null ? null : QTodo.todo.createdAt.loe(createdAtTo);
    }
}