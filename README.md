# deliveryFee

FROM openjdk:11

WORKDIR /app

COPY build/libs/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]