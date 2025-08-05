#!/bin/bash

# H2 메모리 모드로 애플리케이션 실행
./gradlew bootRun --args='--spring.profiles.active=local'