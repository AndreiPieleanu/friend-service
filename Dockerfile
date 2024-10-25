# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

LABEL authors="Andrei"

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build output to the container
COPY target/friend-service-0.0.1-SNAPSHOT.jar /app/friend-service.jar

# Expose the port that the application will run on
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "/app/friend-service.jar"]

# Optional environment variables if needed
# ENV SPRING_PROFILES_ACTIVE=prod
