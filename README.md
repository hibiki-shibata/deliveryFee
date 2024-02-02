# Entry point: 
/app/src/main/kotlin/deliveryfee/App.kt
# Calculation process:
"/app/src/main/kotlin/deliveryfee/CalculateTotalDeliveryFee.kt"

#Dependencies
/app/build.gradle.kts

# specification:
https://github.com/woltapp/engineering-internship-2024

# Prerequisite
gradle(8.5~) and Java(ver19~) installed.

# Testing
<sub>$ gradle test</sub>

# Run test
$ gradle run</sub>

# Build Jar file
<sub>$ gradle build</sub>

# Dockerfile

FROM openjdk:11

WORKDIR /app

COPY ./app/build/libs/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

*build jar file by gradle build before create docker image.
