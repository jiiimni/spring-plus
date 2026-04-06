package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerLogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long requestUserId, Long todoId, Long managerUserId, String requestStatus, String message) {
        Log log = new Log(requestUserId, todoId, managerUserId, requestStatus, message);
        logRepository.save(log);
    }
    // 수정: 로그 저장은 항상 독립적인 새 트랜잭션에서 수행
    // 개념 정리: REQUIRES_NEW 는 기존 트랜잭션과 분리된 별도 트랜잭션을 시작하므로,
    // 바깥 로직이 실패해서 롤백돼도 이 로그는 커밋된 상태로 남을 수 있음
}