# Chinese Chess (象棋)

This project is a Java-based implementation of Chinese Chess with a graphical user interface (GUI).

## Features

*   A fully playable game of Chinese Chess.
*   A graphical user interface to display the board and pieces.
*   Behavior-Driven Development (BDD) tests using Cucumber.

## Prerequisites

*   Java 11 or higher
*   Apache Maven

## How to Build

To build the project, run the following command from the `quest-3` directory:

```bash
mvn clean install
```

## How to Run

You can run the application using the following Maven command:

```bash
mvn javafx:run
```

Alternatively, after building the project, you can run the application from the generated JAR file:

```bash
java -jar target/chinese-chess-1.0-SNAPSHOT.jar
```

## How to Run Tests

To run the BDD tests, use the following command:

```bash
mvn test
```

Test reports can be found in the `target/surefire-reports` directory.

## Technologies Used

*   Java
*   JavaFX for the GUI
*   Maven for dependency management and build automation
*   JUnit 5 for unit testing
*   Cucumber for Behavior-Driven Development (BDD)