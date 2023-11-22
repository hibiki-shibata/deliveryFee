# deliveryFee
# main codes are inside of /app/src/main/kotlin/deliveryfee/App.kt



FROM openjdk:11

WORKDIR /app

COPY build/libs/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]