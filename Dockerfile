FROM openjdk:19

WORKDIR /app

COPY app/build/libs/app.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
