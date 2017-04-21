FROM openjdk:8-jre-alpine
# Match path from application.yml ... But shouldn't we just make a "new" path under the docker profile?
#ADD keystore.p12 keystore.p12
ADD target/easy-edit-*.jar /app.jar
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=docker", "/app.jar"]
#CMD ["JAVA_OPTS=-Dspring.profiles.active=docker", "./app.jar"]
