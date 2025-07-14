FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk-alpine AS jre-builder
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Spring Boot + Redis
RUN jdeps --ignore-missing-deps \
    --multi-release 17 \
    --print-module-deps \
    --class-path 'app.jar' \
    app.jar > jre-deps.info

RUN { cat jre-deps.info; \
     echo ",java.desktop,java.sql,java.management,java.naming,java.xml,java.instrument,java.security.jgss,java.net.http"; } \
    | tr -d '\n' \
    | tr ' ' ',' \
    | sed -e 's/,,*/,/g' -e 's/^,//' -e 's/,$//' > jre-deps-clean.info

RUN jlink --strip-debug \
    --compress 2 \
    --no-man-pages \
    --no-header-files \
    --add-modules $(cat jre-deps-clean.info) \
    --output /custom-jre

FROM alpine:latest
WORKDIR /app

COPY --from=jre-builder /custom-jre /opt/jre
COPY --from=build /app/target/*.jar app.jar

ENV PATH="/opt/jre/bin:${PATH}"
RUN adduser -D appuser
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
