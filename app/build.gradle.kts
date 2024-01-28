 plugins {

    `java-library`
    // kotlin("jvm") version "1.9.20"
    // kotlin("jvm") version "2.0.0-Beta3"

    kotlin("plugin.serialization") version "1.9.20"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}


repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("junit:junit:4.12")

    implementation("com.google.guava:guava:32.1.1-jre")

    implementation("io.ktor:ktor-server-netty:1.6.3")
    implementation("io.ktor:ktor-server-core:1.6.3")
    implementation("io.ktor:ktor-serialization:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("io.ktor:ktor-network-tls-certificates:1.6.3")
    
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "feeee.AppKt"

} //this line is telling compiler which file is the entry point in the sauce code


tasks.named<Test>("test") {
    useJUnitPlatform()
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = "feeee.AppKt"
    } // this line is telling compiler about which treat as main file inside the Jar file, the main can be external file

        
        from(sourceSets.main.get().output) //main source has to be included in jar file
        dependsOn(configurations.runtimeClasspath) // and the code is depends on the confgured classpathes
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        }) // this code is finding jar file from the dependencies and find ziptrees which represent content of Jar file
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE // this line make sure that if there is duplicated config file in different dependencies, only one file will be included in Jar so that it won't be duplicated
    
    
}


// // in case you want to specified jar included dependenvcies by manual
// tasks.jar {
//     manifest {
//         attributes(mapOf("Main-Class" to "deliveryfee.AppKt",
//                          "Implementation-Title" to project.name,
//                          "Implementation-Version" to project.version))
//     }
// }