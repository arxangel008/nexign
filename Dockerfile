# Этап 1: Сборка приложения с использованием Maven

FROM maven:3.9.9-eclipse-temurin-21-jammy as builder
WORKDIR /app
COPY . /app

# Сборка проекта

RUN --mount=type=cache,mode=0777,target=/root/.m2/repository \
    mvn -U clean install -Dmaven.repo.local=/root/.m2/repository --batch-mode --errors --fail-at-end --show-version -T 1C -DskipTests=true


FROM bellsoft/liberica-openjdk-alpine:21.0.4
WORKDIR /app
COPY --from=builder --chown=appuser:appuser /app/target/task-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]