# 🧩 Spring Plus 백엔드 과제

> Spring 기반으로 인증/인가, 데이터 처리, 성능 개선을 적용한 백엔드 프로젝트

---

## 🚀 프로젝트 개요

기존 기능이 구현된 프로젝트를 기반으로
**Transactional, JPA, QueryDSL, JWT, AOP, Spring Security** 를 적용하여
문제 해결과 구조 개선을 진행한 과제입니다.

단순 기능 구현이 아닌
👉 **문제 원인 분석 → 구조 개선 → 성능 최적화 → 보안 적용**
과정을 중심으로 진행했습니다.

---

## 🛠️ Tech Stack

### Backend

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![Spring Security](https://img.shields.io/badge/SpringSecurity-6.x-6DB33F)
![JPA](https://img.shields.io/badge/JPA-Hibernate-blue)
![QueryDSL](https://img.shields.io/badge/QueryDSL-5.x-purple)

### Database

![H2](https://img.shields.io/badge/H2-Database-lightgrey)

### Tools

![Postman](https://img.shields.io/badge/Postman-API-orange)
![Git](https://img.shields.io/badge/Git-VersionControl-red)

---

## 🧱 주요 기능

### 1. 인증/인가 (JWT + Spring Security)

* JWT 기반 로그인/인증 처리
* `SecurityContext` 기반 사용자 정보 관리
* `@AuthenticationPrincipal` 활용
* `/admin/**` 관리자 권한 제어

---

### 2. Todo 관리

* Todo 생성 / 조회 / 단건 조회
* 날씨 및 기간 기반 검색 기능
* QueryDSL 기반 검색 API 구현

---

### 3. 댓글 기능

* 댓글 생성 / 조회
* fetch join 적용으로 N+1 문제 해결

---

### 4. 담당자(Manager) 기능

* Todo 생성 시 작성자 자동 등록
* 담당자 추가 / 삭제
* 작성자 권한 검증 로직 적용

---

### 5. 관리자 기능

* 사용자 권한 변경 API
* AOP 기반 관리자 접근 로그 기록

---

## ⚙️ 핵심 구현 및 개선 사항

### ✅ Transactional 문제 해결

* `@Transactional(readOnly = true)` 환경에서 쓰기 작업 실패
* 저장 메서드에 별도 트랜잭션 적용으로 해결

---

### ✅ 동적 검색 조건 처리

* weather / 기간 조건을 optional 파라미터로 처리
* JPQL null 조건 패턴 적용

---

### ✅ JPA Cascade 적용

* Todo 생성 시 Manager 자동 저장
* `CascadeType.PERSIST` 적용

---

### ✅ N+1 문제 해결

* 댓글 조회 시 `fetch join` 적용
* 불필요한 추가 쿼리 제거

---

### ✅ QueryDSL 적용

* JPQL → QueryDSL 전환
* 타입 안정성 확보
* Projection 기반 DTO 조회 구현

---

### ✅ JWT 확장

* nickname claim 추가
* 인증 객체(AuthUser)에 반영

---

### ✅ AOP 적용

* 관리자 권한 변경 API 호출 전 로그 기록
* `@Before` + 포인트컷 정확히 설정

---

### ✅ Spring Security 전환

* 기존 Filter + ArgumentResolver 구조 제거
* SecurityContext 기반 인증 구조로 리팩토링

---

## 🧪 API 테스트

### 인증

* `POST /auth/signup`
* `POST /auth/signin`

---

### Todo

* `POST /todos`
* `GET /todos`
* `GET /todos/{todoId}`

---

### 댓글

* `POST /todos/{todoId}/comments`
* `GET /todos/{todoId}/comments`

---

### 담당자

* `POST /todos/{todoId}/managers`
* `GET /todos/{todoId}/managers`
* `DELETE /todos/{todoId}/managers/{managerId}`

---

### 관리자

* `PATCH /admin/users/{userId}`

---

## 🔐 인증 방식

* 로그인 시 JWT 발급
* 모든 요청에 `Authorization: Bearer {token}` 헤더 포함
* Spring Security에서 인증/인가 처리

---

## 📸 테스트 결과

> Postman을 활용한 API 테스트 진행

* 회원가입 및 로그인 성공
* JWT 토큰 기반 인증 확인
* Todo 생성 및 조회 정상 동작
* 조건 검색 결과 정상 반영
* 관리자 권한에 따른 접근 제어 확인
* AOP 로그 정상 출력

---

## 💡 회고

* 단순 기능 구현이 아닌 **문제 해결 중심 개발 경험**
* JPA, QueryDSL, Security의 역할과 흐름을 실제 코드로 이해
* 인증/인가 구조를 직접 설계하며 백엔드 구조에 대한 이해도 향상

---

# ✨ 한 줄 정리

👉 **Spring 핵심 기술을 기반으로 구조 개선, 성능 최적화, 보안 적용까지 경험한 백엔드 프로젝트**  

[벨로그 링크] https://velog.io/@jiiim_ni/%EB%82%B4%EC%9D%BC%EB%B0%B0%EC%9B%80%EC%BA%A0%ED%94%84-Spring-3%EA%B8%B0-CH-5-%ED%94%8C%EB%9F%AC%EC%8A%A4-Spring-%EA%B3%BC%EC%A0%9C

---
