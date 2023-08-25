Atipera Interview Program
---


The Atipera Interview Program is a robust application that leverages data from the GitHub API. The application exposes endpoints on port 8080, facilitating seamless interaction with GitHub repositories.

Requirements
---

Before getting started, ensure you have the following:

- Java 17
- Active Internet Connection

Installation
------------

You can effortlessly set up the application using the following steps:

### Manual
1. Build the Application

Use Gradle to build the application. Open terminal in project folder:
```
./gradlew build
```
If command denied on linux run
```
chmod +x gradlew
```
2. Run the Application

Execute the application from the terminal. Provide your GitHub API key and the desired API version as environment variables:
- API_KEY - Github api Key
- API_VERSION - eg. "2022-11-28"
```
java -DAPI_KEY="YOUR_API_KEY" -DAPI_VERSION="YOUR_API_VERSION" -jar  build/libs/interview-0.0.1-SNAPSHOT.jar
```

### Docker
1. Build docker image

Open terminal in project folder and execute a command below. The command will
build docker image based on Dockerfile. To learn more visit 
[Docker documentation](https://docs.docker.com/engine/reference/commandline/build/)

```
docker build -t interview .
```

2. Run docker container with environment variables

In opened terminal run command below. The command will run image in docker container with 
provided environment variables. Created container name will be interview. To learn more visit 
[docker documentation](https://docs.docker.com/engine/reference/commandline/run/)
- API_KEY - Github api Key
- API_VERSION - eg. "2022-11-28"
```
docker run -e "API_KEY=YOUR_API_KEY" -e "API_VERSION=YOUR_API_VERSION" -p 8080:8080 --name interview interview
```

Usage
-----

The application is accessible via http://localhost:8080. It offers the following endpoints:

Endpoints
---

### User repositories with branches 

Retrieve a list of a user's repositories along with their branches.

Code sample <span style="display: inline-block; padding: 3px 6px; background-color: blue; color: white; border: 1px solid blue; border-radius: 15px">GET</span>
  /api/v1/repositories
- with request param user
- produce only json

e.g.
```
localhost:8080/api/v1/repositories?user=qaqa85
```

cURL
```
curl --location 'localhost:8080/api/v1/repositories?user=qaqa85' \
--header 'Accept: application/json'
```

### Example response status 200
```json
[
  {
    "repository": "apartmentsApp",
    "owner": "qaqa85",
    "branches": [
      {
        "name": "develop",
        "lastCommitSha": "ad75b074f82f052f61b1ba50a86dc3cc174a8a99"
      },
      {
        "name": "master",
        "lastCommitSha": "2289cdbb330d221589c59c7b12dfe173eb7a44a9"
      }
    ]
  }
]
```
### Example response status 404 (user not found)
```json
{
    "statusCode": 404,
    "message": "User qaqa8585 not found"
}
```
### Example response status 406 (when Accept application/xml header)
```json
{
    "statusCode": 406,
    "message": "Invalid accept header"
}
```

