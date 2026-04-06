package org.example.expert.domain.comment.repository;

import org.example.expert.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 수정: JOIN -> JOIN FETCH 로 변경해서 댓글 조회 시 작성자(User)도 함께 조회
     * 개념 정리: fetch join은 연관 엔티티를 지연 로딩하지 않고 한 번의 쿼리로 같이 가져오는 방식
     * 그래서 comment.getUser()를 반복문에서 호출해도 추가 쿼리가 발생하지 않아 N+1 문제를 줄일 수 있음
     */

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.todo.id = :todoId")
    List<Comment> findByTodoIdWithUser(@Param("todoId") Long todoId);
}