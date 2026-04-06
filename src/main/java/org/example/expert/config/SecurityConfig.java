package org.example.expert.config;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 수정: JWT 기반 인증은 세션을 사용하지 않으므로 STATELESS 설정
                // 개념 정리: 서버가 로그인 상태를 세션에 저장하지 않고,
                // 요청마다 JWT를 검증해서 인증을 처리하는 구조

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
                )
                // 수정: 관리자 경로는 ADMIN 권한 필요, 나머지는 인증 필요
                // 개념 정리: 인가는 "누가 들어올 수 있는가"를 정하는 단계이고,
                // Spring Security에서는 requestMatchers로 URL별 권한 정책을 선언함

                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}