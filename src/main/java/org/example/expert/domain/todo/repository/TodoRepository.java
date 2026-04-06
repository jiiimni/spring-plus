package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user " +
            "WHERE (:weather IS NULL OR t.weather = :weather) " +
            "AND (:modifiedAtFrom IS NULL OR t.modifiedAt >= :modifiedAtFrom) " +
            "AND (:modifiedAtTo IS NULL OR t.modifiedAt <= :modifiedAtTo) " +
            "ORDER BY t.modifiedAt DESC",
            countQuery = "SELECT COUNT(t) FROM Todo t " +
                    "WHERE (:weather IS NULL OR t.weather = :weather) " +
                    "AND (:modifiedAtFrom IS NULL OR t.modifiedAt >= :modifiedAtFrom) " +
                    "AND (:modifiedAtTo IS NULL OR t.modifiedAt <= :modifiedAtTo)")
    Page<Todo> searchTodos(
            @Param("weather") String weather,
            @Param("modifiedAtFrom") LocalDateTime modifiedAtFrom,
            @Param("modifiedAtTo") LocalDateTime modifiedAtTo,
            Pageable pageable
    );

    /**
     *
     * 수정: weather / modifiedAt 시작일 / modifiedAt 종료일을 optional 조건으로 검색하는 JPQL 추가
     * 개념 정리:
     * (:param IS NULL OR field = :param)
     * 이 패턴은 "값이 없으면 전체 허용, 값이 있으면 그 조건으로 검색" 이라는 뜻
     */

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
    /**
    * 수정: 단건 조회에서도 user 를 함께 조회하도록 fetch join 유지
    * 개념 정리: 연관 엔티티를 함께 조회하면 LAZY 로딩 때문에 추가 쿼리가 나가는 것을 줄일 수 있음
    */