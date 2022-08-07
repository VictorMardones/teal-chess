# Building the project

As with many Gradle projects, a Gradle wrapper is included.
Replace `./gradlew` with `.\gradlew.bat` if you're using Windows.

## Using the program

### Compiling a JAR

To generate a JAR with every dependency included, the [Gradle Shadow Plugin](https://imperceptiblethoughts.com/shadow/) can be used:

```shell
./gradlew shadowJar
```

The generated JAR can be found in `build/libs/teal-chess-0.0-all.jar`.
No other file is required to run the program.

### Running the JAR

The JAR requires JRE (or JDK) version 17 or higher.
To start the program, run:

```shell
java -jar teal-chess-0.0-all.jar
```

To check other command line options, run:

```shell
java -jar teal-chess-0.0-all.jar --help
```

## Development tools

### Applying Google Java Format

Google Java format is applied automatically every time the project is compiled.
To apply it manually, run:

```shell
./gradlew spotlessApply
```

### Running unit tests

To run every unit test, run:

```shell
./gradlew test
```

A [JaCoCo](https://www.jacoco.org/jacoco/) test coverage report is generated after running the tests, which can be found at `build/reports/jacoco/test`.

### Generating a Javadoc

To generate a Javadoc for the project's code and see what files are missing documentation, run:

```shell
./gradlew javadoc
```

The generated Javadoc can be found at `build/docs/javadoc`.

### Running in debug mode

To run the program in debug mode, which enables logging, run:

```shell
java -jar teal-chess-0.0-all.jar --debug
```

### Using SonarQube

For checking code quality and security, the Gradle SonarQube Plugin is included.
I suggest reading [the documentation](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-gradle/) to learn how to use it.

### Cleaning the build directory

To clean the `build` directory, run:

```shell
./gradlew clean
```
