#!/bin/bash

# PostgreSQL 모드로 애플리케이션 실행
./gradlew bootRun --args='--spring.profiles.active=postgres'