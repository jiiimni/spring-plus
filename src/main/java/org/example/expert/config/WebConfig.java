package org.example.expert.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 수정: 기존 AuthUserArgumentResolver 등록 제거
    // 개념 정리: Spring Security 전환 후에는 사용자 인증 정보를
    // ArgumentResolver가 아니라 @AuthenticationPrincipal로 주입받으므로
    // 별도의 커스텀 ArgumentResolver 등록이 더 이상 필요 없음
}