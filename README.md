# 챗봇 서비스

## 프로젝트 소개

AI 챗봇 서비스로, 사용자 관리, 대화 관리, 피드백 관리, 분석 및 보고 기능을 제공합니다.

## 기술 스택

- Kotlin 1.9.25
- Spring Boot 3.5.4
- Spring Security
- Spring Data JPA
- JWT 인증
- H2 Database / PostgreSQL 15.8+

## 실행 방법

### H2 메모리 모드로 실행 (기본)

```bash
./gradlew bootRun --args='--spring.profiles.active=memory'
```

### PostgreSQL 모드로 실행

1. Docker Compose로 PostgreSQL 실행

```bash
docker-compose up -d
```

2. 애플리케이션 실행

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

## H2 콘솔 접속

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:sionic
- 사용자명: sa
- 비밀번호: (비어 있음)
## pgAdmin 접속 (PostgreSQL 모드)

- URL: http://localhost:5050
- 이메일: admin@sionic.com
- 비밀번호: admin

## 기본 계정

1. 관리자

   - 이메일: admin@sionic.com
   - 비밀번호: admin123

2. 일반 사용자
   - 이메일: user@sionic.com
   - 비밀번호: user123

## API 문서

- Swagger UI: http://localhost:8080/swagger-ui/index.html

## ERD 문서

ERD 문서는 `docs` 디렉토리에서 확인할 수 있습니다.

## 과제 진행 회고

### 1. 과제 분석 방법

이 프로젝트는 AI 챗봇 서비스를 구현하는 과제로, 다음과 같은 핵심 요구사항을 분석했습니다:

- 사용자 관리 및 인증: 회원가입, 로그인, JWT 토큰 인증
- 채팅 관리: 스레드 기반 대화 관리, 30분 타임아웃 로직
- 피드백 관리: 사용자별 채팅에 대한 피드백 기능
- 분석 및 보고: 관리자용 사용자 활동 기록 및 보고서 생성 기능

기존 프로젝트 구조를 참고하여 패키지 구조를 설계하고, 각 기능별로 필요한 엔티티와 API를 구현했습니다.

### 2. AI 활용 및 어려움

AI를 활용하여 다음과 같은 작업을 수행했습니다:

- 초기 프로젝트 구조 설계 및 엔티티 관계 정의
- 기본적인 CRUD 기능 구현을 위한 코드 생성
- 복잡한 비즈니스 로직(스레드 타임아웃, 피드백 중복 방지 등) 구현

어려웠던 점:

- 기존 프로젝트 구조와의 일관성 유지
- 단일 책임 원칙(SRP)에 맞춰 DTO, Command, Info 객체 분리 작업
- 시간 관련 데이터 타입(Instant, LocalDate)의 일관성 있는 사용

### 3. 가장 어려웠던 기능

3시간이라는 짧은 시간이 많이 아쉽게 느껴쪘습니다. 완성도가 많이 아쉬운 부분들이 많은 것 같습니다.
