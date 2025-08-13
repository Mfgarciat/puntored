FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY puntored .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/puntored/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
