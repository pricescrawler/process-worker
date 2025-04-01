FROM maven:3.9.6-amazoncorretto-21 AS builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM amazoncorretto:24
LABEL PROJECT_NAME=prices-crawler-process-worker
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]

COPY data/ data/
