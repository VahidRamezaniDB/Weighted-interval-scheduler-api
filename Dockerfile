FROM openjdk:17-alpine
ADD target/scheduler-api*.jar /opt/scheduler-api.jar
RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "curl"]
ENTRYPOINT ["java", "-jar", "/opt/scheduler-api.jar"]
