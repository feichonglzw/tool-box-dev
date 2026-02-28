FROM maven:3.9-eclipse-temurin-17 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=backend-build /app/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]