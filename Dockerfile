# Stage 1: build with Maven + Temurin 21 JDK
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copy pom first to leverage layer caching for dependencies
COPY pom.xml ./

# Copy source code
COPY src ./src

# Build the application (skip tests to speed up image builds)
RUN mvn -B package -DskipTests

# Stage 2: runtime (small JRE 21)
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# Copy the built jar from the builder stage. The artifact name is derived from pom.xml
COPY --from=builder /workspace/target/requesttracker-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/app.jar"]
