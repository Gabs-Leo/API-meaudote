FROM maven:3-eclipse-temurin-22-alpine as build
COPY . .
RUN mvn clean install -Dmaven.test.skip

FROM openjdk:22
COPY --from=build target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]