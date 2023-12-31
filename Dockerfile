FROM openjdk:17-alpine
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} prophius-social-media-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "prophius-social-media-api.jar"]