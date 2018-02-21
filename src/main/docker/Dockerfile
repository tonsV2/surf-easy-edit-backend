FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/tonsV2/surf-easy-edit-backend.git .

FROM openjdk:8-jdk-alpine as build
#COPY --from=clone /app /app
ADD . /app
WORKDIR /app
RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=build /app/target/easy-edit-*.jar .
CMD java -jar *.jar
