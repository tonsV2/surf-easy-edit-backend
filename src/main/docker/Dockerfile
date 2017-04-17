FROM openjdk:8-jre-alpine
ADD target/easy-edit-*.jar /app.jar
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=docker", "/app.jar"]
