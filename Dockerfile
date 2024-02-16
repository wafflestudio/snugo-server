FROM eclipse-temurin:17-jdk-alpine
ARG DB_URI
ARG JWT_SECRET
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT java -Dspring.profiles.active=prod -Dspring.data.mongodb.uri=${DB_URI} -Djwt.secret=${JWT_SECRET} -jar /app.jar
