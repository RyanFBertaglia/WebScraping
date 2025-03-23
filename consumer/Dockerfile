FROM eclipse-temurin:17-jdk-alpine AS jre-builder

WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw .
RUN chmod +x mvnw

COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

COPY src/ ./src/
RUN ./mvnw package -DskipTests

RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 17 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    target/*.jar > modules.txt

RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules $(cat modules.txt) \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /optimized-jdk-17

FROM alpine:latest

ENV JAVA_HOME=/opt/jdk/jdk-17
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copia JRE customizado
COPY --from=jre-builder /optimized-jdk-17 $JAVA_HOME

ARG APPLICATION_USER=spring
RUN addgroup -S $APPLICATION_USER && \
    adduser -S $APPLICATION_USER -G $APPLICATION_USER && \
    mkdir /app && \
    chown -R $APPLICATION_USER:$APPLICATION_USER /app

COPY --from=jre-builder --chown=$APPLICATION_USER:$APPLICATION_USER /opt/app/target/*.jar /app/app.jar

WORKDIR /app
USER $APPLICATION_USER

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]