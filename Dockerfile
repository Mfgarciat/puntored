FROM eclipse-temurin:17-jdk-focal as build

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-focal

COPY --from=build /app/target/puntored-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
