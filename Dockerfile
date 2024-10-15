FROM amazoncorretto:17
COPY target/bloood-donation-0.0.1-SNAPSHOT.jar /app/blood-donation.jar
WORKDIR /app
CMD ["java", "-jar", "blood-donation.jar"]