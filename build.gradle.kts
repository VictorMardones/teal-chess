plugins {
    java
    jacoco
    id("org.sonarqube") version "3.4.0.2513"
    id("com.diffplug.spotless") version "6.8.0"
}

group = "cl.vmardones"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")

    implementation("org.apache.xmlgraphics:batik-all:1.14")
    implementation("org.apache.xmlgraphics:batik-swing:1.14")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")

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

tasks.compileJava {
    dependsOn(tasks.spotlessApply)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
    }
}

tasks.sonarqube {
    dependsOn(tasks.jacocoTestReport)
}

spotless {
    java {
        googleJavaFormat()
    }

    format("misc") {
        target("**/*.gradle.kts", "**/*.md", "**/.gitignore")

        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()
    }
}
