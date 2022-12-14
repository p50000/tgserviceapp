FROM openjdk:17
COPY build/libs/tgservice-0.0.1-SNAPSHOT.jar tgservice.jar 
ENTRYPOINT "java" "-jar" "tgservice.jar"
