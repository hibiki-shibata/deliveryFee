# deliveryFee
# main codes are inside of /app/src/main/kotlin/deliveryfee/App.kt

specification:
https://github.com/woltapp/engineering-internship-2024

FROM openjdk:11

WORKDIR /app

COPY ./app/build/libs/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


Might be good to module the main code with "Class" to make it scalable.