FROM amazoncorretto:17-alpine3.16 AS GRADLE_BUILD
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source

RUN chmod +x ./gradlew
RUN ./gradlew build

FROM amazoncorretto:17-alpine3.16
COPY --from=GRADLE_BUILD /app/source/build/libs/interview-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]