FROM eclipse-temurin:17-jdk-alpine
# COPY build/libs/*.jar app.jar
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","app.jar"]
