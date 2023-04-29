# Use a Java runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the Spring Boot executable JAR file to the container
COPY target/petshop-0.0.1-SNAPSHOT.jar .

# Install required packages
RUN apt-get update && \
    apt-get install -y curl gnupg

# Install Redis client
RUN apt-get install -y redis-tools

# Expose port for Spring Boot
EXPOSE 8080

# Start the Spring Boot application
CMD java -jar petshop-0.0.1-SNAPSHOT.jar