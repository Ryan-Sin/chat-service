-- 관리자 계정 추가 (비밀번호: admin123)
INSERT INTO users (email, password, name, role, created_at)
VALUES ('admin@sionic.com', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOSsqE1asOHXG1uiKu3RnBXjC.X.K', '관리자', 'ADMIN', CURRENT_TIMESTAMP);

-- 일반 사용자 계정 추가 (비밀번호: user123)
INSERT INTO users (email, password, name, role, created_at)
VALUES ('user@sionic.com', '$2a$10$yfIUkGYFIyU3m/DRHrEG9.fhdjkKfLAJwQUMWfL9wxGhAI2mxzIAa', '사용자', 'MEMBER', CURRENT_TIMESTAMP);