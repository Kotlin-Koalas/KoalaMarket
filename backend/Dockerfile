FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /api

# Copy Gradle build files
COPY build.gradle settings.gradle gradlew ./
COPY gradle/ ./gradle/

# Copy the entire source code
COPY src ./src

# Build the application
RUN ./gradlew build

# Second stage: create the final image
FROM eclipse-temurin:17-jdk-alpine 

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/your-application.jar ./app.jar

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]