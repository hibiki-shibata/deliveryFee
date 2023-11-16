FROM openjdk:19

WORKDIR /app

COPY app/build/libs/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]