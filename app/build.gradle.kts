plugins {
    
    kotlin("jvm") version "2.0.0-Beta3" // kotlin("jvm") version "1.9.20" // stable
    kotlin("plugin.serialization") version "1.9.20"
    application  // Apply the application plugin to add support for building a CLI application in Java.
}


repositories {
    mavenCentral()
}


dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation("io.ktor:ktor-server-netty:1.6.3")
    implementation("io.ktor:ktor-server-core:1.6.3")
    implementation("io.ktor:ktor-serialization:1.6.3")  
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


//this line is telling compiler which file is the entry point 
application {
    mainClass = "indexfile.AppKt"
}


tasks.named<Test>("test") {
    useJUnitPlatform()
}


tasks.jar {
    // this line is telling compiler about which treat as main file inside the Jar file, the main can be external file
    manifest {
        attributes["Main-Class"] = "indexfile.AppKt"
    } 
            //main source has to be included in jar file
        from(sourceSets.main.get().output) 
            // this code is depends on the confgured classpathes
        dependsOn(configurations.runtimeClasspath) 
            // this is finding jar file from the dependencies and find ziptrees which represent content of Jar file
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
            // this line make sure that if there is duplicated config file in different dependencies, only one file will be included in Jar so that it won't be duplicated
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE 
    
}

