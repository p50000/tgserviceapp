FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tgservice.jar
ENTRYPOINT "java" "-jar" "tgservice.jar"