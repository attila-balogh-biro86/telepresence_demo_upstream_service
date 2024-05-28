# Use an official Gradle image to build the project with JDK 21
FROM gradle:jdk21 AS builder

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy the Gradle build files and the source code
COPY --chown=gradle:gradle build.gradle settings.gradle ./
COPY --chown=gradle:gradle src ./src

# Build the project using Gradle
RUN gradle build --no-daemon

# Use an official OpenJDK runtime image with JDK 21
FROM openjdk:21-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8080

ENV EXTERNAL_API_HOSTNAME=service-downstream:8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]