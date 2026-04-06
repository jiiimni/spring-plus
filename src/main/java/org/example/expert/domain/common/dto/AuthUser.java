package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserRole userRole;

    public AuthUser(Long id, String email, String nickname, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
    }
}

/**
 * 수정: 인증 객체에 nickname 필드 추가
 * 개념 정리: AuthUser는 로그인한 사용자의 핵심 정보를 담는 객체라서,
 * JWT에 nickname을 넣었다면 여기에도 nickname이 있어야 이후 서비스 로직에서 사용할 수 있음
 */