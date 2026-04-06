package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestUserId;
    private Long todoId;
    private Long managerUserId;
    private String requestStatus;
    private String message;

    public Log(Long requestUserId, Long todoId, Long managerUserId, String requestStatus, String message) {
        this.requestUserId = requestUserId;
        this.todoId = todoId;
        this.managerUserId = managerUserId;
        this.requestStatus = requestStatus;
        this.message = message;
    }
    // 수정: 매니저 등록 요청 로그를 저장할 엔티티 추가
    // 개념 정리: 과제 요구사항에서 로그 테이블명은 log 이고,
    // 생성 시간은 반드시 필요하므로 Timestamped를 상속받아 createdAt을 함께 관리
}