FROM maven:3-openjdk-18 as build
COPY . .
RUN mvn clean install -Dmaven.test.skip

FROM openjdk
COPY --from=build target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]