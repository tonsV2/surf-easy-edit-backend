# Build using Jib
Build surf-easy-edit-frontend using `npm run build` and make sure the path in `pom.xml:112` is set accordingly. Afterwards run the below command to build and push a new image to Docker Hub.
```bash
./mvnw clean compile jib:build
```

# The image can be tested locally by running
```bash
docker pull tons/surf-easy-edit && docker-compose up
```

# Run tests
```bash
SPRING_PROFILES_ACTIVE=test ./mvnw test
```

# Deploy
https://github.com/tonsV2/surf-easy-edit-chart

https://github.com/tonsV2/nuc-stack

## Create new user
* Duplicate row from users table
* http://easyedit.surfstation.dk/#/users
* 

# Get edit id
set EDIT_ID (http :8080/api/users | jq -r '.[] | select(.username == "helsingoerhavn") | .editId')
# Post content
echo -n "Some content" | http :8080/api/edit/$EDIT_ID
# Get all content
http ':8080/api/feed?username=helsingoerhavn'
# Get latest content
http ':8080/api/feed/latest?username=helsingoerhavn'
