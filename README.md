# File structure
## Source directory
./app/src/main/kotlin/deliveryfee/*
### Entry point: 
./app/src/main/kotlin/deliveryfee/App.kt
### Server configuration: 
./app/src/main/kotlin/deliveryfee/server.kt
### Verify requested values are valid:
./app/src/main/kotlin/deliveryfee/reqDataVerify.kt
### Calculation process:
./app/src/main/kotlin/deliveryfee/CalculateTotalDeliveryFee.kt

## Test directory:
./app/src/test/kotlin/deliveryfee/*

## Dependencies:
./app/build.gradle.kts

# Specification
https://github.com/woltapp/engineering-internship-2024

# Prerequisite
Gradle(8.5~) and Java(ver19~) installed.

# Testing
## Unit test: 
```bash
$ gradle test
```
## API test:
```bash
$ gradle run
```
Import <sup>./API_testing/postman.json</sup> into [POSTMAN](https://www.postman.com/).
and run the folder in Postman

# Build Jar file
```bash
$ gradle build
```


