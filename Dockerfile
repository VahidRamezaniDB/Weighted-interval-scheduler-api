FROM openjdk:17-alpine
ADD target/scheduler-api*.jar /opt/scheduler-api.jar
ENTRYPOINT ["java", "-jar", "scheduler-api.jar"]