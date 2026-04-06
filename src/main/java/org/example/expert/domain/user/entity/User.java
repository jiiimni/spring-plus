package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.user.enums.UserRole;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String nickname;
    // 수정: User 테이블에 nickname 컬럼 추가
    // 개념 정리: 엔티티에 필드를 추가하면 DB 테이블의 컬럼과 매핑되어 사용자 닉네임을 저장할 수 있음

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String email, String password, String nickname, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }
    // 수정: 회원가입 시 nickname도 함께 저장할 수 있도록 생성자 변경

    private User(Long id, String email, String nickname, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
    }
    // 수정: 인증 객체(AuthUser)에서 User로 변환할 때 nickname도 유지되도록 생성자 변경

    public static User fromAuthUser(AuthUser authUser) {
        return new User(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getNickname(),
                authUser.getUserRole()
        );
    }
    // 수정: JWT/인증 객체에 담긴 nickname 정보를 User 객체에도 반영

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}