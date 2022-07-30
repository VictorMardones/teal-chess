plugins {
    java
    jacoco
    id("org.sonarqube") version "3.4.0.2513"
    id("com.diffplug.spotless") version "6.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "cl.vmardones"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")

    implementation("org.apache.xmlgraphics:batik-all:1.14")
    implementation("org.apache.xmlgraphics:batik-swing:1.14")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")

    implementation("com.miglayout:miglayout-swing:11.0")

    implementation("com.formdev:flatlaf:2.4")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testCompileOnly("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")

    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks {
    compileJava {
        dependsOn(spotlessApply)
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }

    test {
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)

        reports {
            xml.required.set(true)
        }
    }

    this.sonarqube {
        dependsOn(jacocoTestReport)
    }

    shadowJar {
        manifest {
            attributes("Main-Class" to "launcher.Chess")
            attributes("Implementation-Title" to project.name)
        }
    }

    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}

spotless {
    java {
        licenseHeaderFile("license.template")
        palantirJavaFormat()
    }

    format("misc") {
        target("**/*.gradle.kts", "**/*.md", "**/.gitignore")

        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()
    }
}

shadow {

}
