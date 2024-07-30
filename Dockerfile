FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/RegistrationForWorkshops-0.0.1-SNAPSHOT.jar ./RegistrationForWorkshops-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "RegistrationForWorkshops-0.0.1-SNAPSHOT.jar"]
