FROM openjdk:17.0.2-slim-buster as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
ARG DB_FILE=folks.sqlite
COPY ${JAR_FILE} application.jar
COPY ${DB_FILE} db.sqlite
RUN java -Djarmode=layertools -jar application.jar extract
FROM openjdk:17.0.2-slim-buster
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
COPY --from=builder application/db.sqlite/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]