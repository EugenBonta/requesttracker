# Use a slim JRE runtime image
FROM eclipse-temurin:26-jre-jammy

# Create app dir
WORKDIR /app

# Copy the jar produced by mvn package
COPY target/requesttracker-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the app uses
EXPOSE 8081

# Optional healthcheck for docker
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]
