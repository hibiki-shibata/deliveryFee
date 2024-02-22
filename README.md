# File structure
## Source directory
`./app/src/main/kotlin/deliveryfee/*`
#### Entry point: 
`./app/src/main/kotlin/deliveryfee/App.kt`
#### Server configuration: 
`./app/src/main/kotlin/deliveryfee/Server.kt`
#### Verify requested values are valid:
`./app/src/main/kotlin/deliveryfee/ReqDataVerify.kt`
#### Verify response values are valid:
`./app/src/main/kotlin/deliveryfee/ResDataVerify.kt`
#### Calculation process:
`./app/src/main/kotlin/deliveryfee/deliveryFeeCalculations/CalculateTotalDeliveryFee.kt`

## Test directory:
`./app/src/test/kotlin/deliveryfee/*`

## Dependencies:
`./app/build.gradle.kts`

# Specification
https://github.com/woltapp/engineering-internship-2024

# Prerequisite
[Gradle](https://gradle.org/)(8.5~) and Java(ver19~) installed.

# Testing
## Unit test: 
```bash
$ gradle test
```
## API test:
```bash
$ gradle run
```
Import `./API_testing/postman.json` into [POSTMAN](https://www.postman.com/).
and run the folder in Postman

# Build Jar file
```bash
$ gradle build
```
To run the Built jar file, you can use this command
```bash
java -jar ~/deliveryFee/app/build/libs/app.jar
```

Author: hibiki.shibata@wolt.com
