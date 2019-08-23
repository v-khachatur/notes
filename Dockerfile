FROM openjdk:8-jdk-alpine
ADD /target/notes-0.0.1-SNAPSHOT.jar notes.jar
ENTRYPOINT ["java", "-jar", "/notes.jar"]