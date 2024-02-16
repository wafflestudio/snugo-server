FROM eclipse-temurin:17-jdk-alpine
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT java -Dspring.profiles.active=prod -Dspring.data.mongodb.username=${DB_USERNAME} -Dspring.data.mongodb.password=${DB_PASSWORD} -Djwt.secret=${JWT_SECRET} -jar /app.jar
