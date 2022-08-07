
// Project metadata
group = "cl.vmardones"
version = "0.0"

repositories {
    mavenCentral()
}

plugins {
    java

    // Test coverage reports
    jacoco

    // Code quality and security
    id("org.sonarqube") version "3.4.0.2513"

    // Code formatting
    id("com.diffplug.spotless") version "6.8.0"

    // Building "fat" JARs
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

spotless {
    java {
        licenseHeaderFile("license.template")
        googleJavaFormat()
    }

    format("misc") {
        target("**/*.gradle.kts", "**/*.config", "**/*.md", "**/.gitignore")

        indentWithSpaces(4)
        trimTrailingWhitespace()
        endWithNewline()
    }
}

dependencies {
    // Immutable collections
    implementation("com.google.guava:guava:31.1-jre")

    // Loading SVG files
    implementation("org.apache.xmlgraphics:batik-all:1.14")
    implementation("org.apache.xmlgraphics:batik-swing:1.14")

    // Logging
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")

    // CLI
    implementation("info.picocli:picocli:4.6.3")
    annotationProcessor("info.picocli:picocli-codegen:4.6.3")

    // GUI
    implementation("com.miglayout:miglayout-swing:11.0")
    implementation("com.formdev:flatlaf:2.4")

    // Boilerplate reduction (where records aren't enough)
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    // Unit testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testCompileOnly("org.junit.jupiter:junit-jupiter-params:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    // Mocking
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
    testImplementation("org.mockito:mockito-inline:4.6.1")

    // Better assertions
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks {
    compileJava {
        dependsOn(spotlessApply)

        // Use project group and name as the base package for all source code
        options.compilerArgs = options.compilerArgs + listOf("-Aproject=${project.group}/${project.name}")
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }

    // Generate a report after every test
    test {
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)

        reports {
            // Required to make Sonarqube understand the results
            xml.required.set(true)
        }
    }

    this.sonarqube {
        dependsOn(jacocoTestReport)
    }

    shadowJar {
        manifest {
            attributes("Main-Class" to "cl.vmardones.chess.launcher.Chess")
            attributes("Implementation-Title" to project.name)
            attributes("Implementation-Version" to project.version)
        }
    }

    // Reproducible JAR builds (https://imperceptiblethoughts.com/shadow/configuration/reproducible-builds/)
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}
