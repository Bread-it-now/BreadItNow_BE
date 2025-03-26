FROM openjdk:17-slim
WORKDIR /app

ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
