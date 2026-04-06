/**
 * 이제 일반 서블릿 필터 등록이 아니라 Spring Security 필터 체인 안에 JWT 필터를 넣을 거라서 삭제
 * package org.example.expert.config;
 *
 * import lombok.RequiredArgsConstructor;
 * import org.springframework.boot.web.servlet.FilterRegistrationBean;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 *
 * @Configuration
 * @RequiredArgsConstructor
 * public class FilterConfig {
 *
 *     private final JwtUtil jwtUtil;
 *
 *     @Bean
 *     public FilterRegistrationBean<JwtFilter> jwtFilter() {
 *         FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
 *         registrationBean.setFilter(new JwtFilter(jwtUtil));
 *         registrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴을 지정합니다.
 *
 *         return registrationBean;
 *     }
 * }
 */