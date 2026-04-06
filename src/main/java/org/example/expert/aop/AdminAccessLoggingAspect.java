package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminAccessLoggingAspect {

    private final HttpServletRequest request;

    @Before("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public void logBeforeChangeUserRole(JoinPoint joinPoint) {
        String userId = String.valueOf(request.getAttribute("userId"));
        String requestUrl = request.getRequestURI();
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("Admin Access Log - User ID: {}, Request Time: {}, Request URL: {}, Method: {}",
                userId, requestTime, requestUrl, joinPoint.getSignature().getName());
    }
}

/**
 * 수정 1: @After -> @Before 로 변경
 * 개념 정리: @Before 는 대상 메서드가 실행되기 전에 동작하고, @After 는 실행된 뒤에 동작함
 * 이번 과제는 changeUserRole() 실행 전에 로그를 남겨야 하므로 @Before 가 맞음
 *
 * 수정 2: 포인트컷 대상을 UserController.getUser(..) 에서
 * UserAdminController.changeUserRole(..) 로 변경
 * 개념 정리: AOP는 "어떤 메서드에 적용할지"를 execution 표현식으로 정확히 지정해야 함
 * 대상 클래스나 메서드명이 틀리면 AOP가 아예 동작하지 않음
 */