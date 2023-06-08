FROM openjdk:11
WORKDIR /app
COPY target/facturier.jar app.jar
COPY uploads /app/uploads
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
EXPOSE 8082
