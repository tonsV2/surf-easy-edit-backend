# Run app
./mvnw spring-boot:run

# Run with https
./mvnw spring-boot:run -Drun.profiles=https

https://localhost:8443/api/posts/filter?username=tons

# Docker
## Build image
./mvnw -DskipTests clean package docker:build

## Run container
docker-compose down && docker-compose up -d --build
