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

# Deploy

## Openshift
 * Merge master into openshift and change to that branch
 * npm run build
 * copy the content of dist/ to /src/main/resource/static/
 * Commit everything to the branch openshift
 * git push (prod|dev) openshift:master # Push the local branch openshift to the remote branch master

## Heroku
 * heroku create --region eu
 * heroku config:set SPRING_PROFILES_ACTIVE=heroku
 * git push --force heroku openshift:master

### https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku
