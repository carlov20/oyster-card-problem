## How to run the code
- This project uses Java 8 so please have Java installed.
- This project uses Lombok, please visit the [project lombok website](https://projectlombok.org/) and go to Install on how to install with the IDE used.
- To build the project please execute `./gradlew clean build`. Once built you can execute the jar by executing `java -jar build/libs/oyster-card-problem-1.0-SNAPSHOT.jar`
- Alternatively if your IDE allows it execute the main() method on the [MainApplication Java class](src/main/java/org/oyster/card/MainApplication.java)
- To run the tests please execute `./gradlew clean test`. This will produce a jacoco and junit report found in the [build report folder](build/reports)
