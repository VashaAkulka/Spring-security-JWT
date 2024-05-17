FROM astita/openjdk17_jdk-alpine
ARG JAR_FILE=build/libs/Spring-security-0.0.1-SNAPSHOT.jar
WORKDIR app/
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]