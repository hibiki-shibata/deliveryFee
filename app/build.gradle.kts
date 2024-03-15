plugins {
    kotlin("jvm") version "2.0.0-Beta3" // kotlin("jvm") version "1.9.20" is stable
    kotlin("plugin.serialization") version "1.9.20" 
    application  // Apply the application plugin to add support for building a CLI application in Java.
}


repositories {
    mavenCentral()
}


dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
  

    implementation("io.ktor:ktor-server-netty:1.6.8")
    implementation("io.ktor:ktor-server-core:1.6.8")
    implementation("io.ktor:ktor-serialization:1.6.8")   
}


// java {
//     toolchain {
//         languageVersion = JavaLanguageVersion.of(21)
//     }
// }

kotlin {
    jvmToolchain(21)
}


//this line is telling compiler the entry point 
application {
    mainClass = "AppKt.AppKt"
}


tasks.jar {
    // this line is telling compiler main file inside the Jar file
    manifest {
        attributes["Main-Class"] = "AppKt.AppKt"
    } 
            //main source has to be included in jar file
        from(sourceSets.main.get().output) 
            // source code is depends on the confgured classpathes
        dependsOn(configurations.runtimeClasspath) 
            // this is finding jar file from the dependencies and find ziptrees which represent content of Jar file
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
            // this line make sure that if there is duplicated config file in different dependencies, only one file will be included in Jar so that it won't be duplicated
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE 
    
}

