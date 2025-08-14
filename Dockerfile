# Dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the bootable jar (not the .original)
COPY target/app.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
