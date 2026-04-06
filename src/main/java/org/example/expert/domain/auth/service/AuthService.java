package org.example.expert.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getNickname(),
                userRole
        );
        // 수정: 회원 생성 시 nickname도 함께 저장

        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getNickname(),
                userRole
        );
        // 수정: 회원가입 후 발급하는 JWT에도 nickname 포함

        return new SignupResponse(bearerToken);
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("가입되지 않은 유저입니다."));

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserRole()
        );
        // 수정: 로그인 시 발급하는 JWT에도 nickname 포함
        // 개념 정리: 토큰은 로그인 이후 사용자의 신분증 역할을 하므로,
        // 프론트에서 닉네임을 바로 쓰려면 로그인 토큰에도 nickname이 들어 있어야 함

        return new SigninResponse(bearerToken);
    }
}