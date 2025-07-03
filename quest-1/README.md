
# E-commerce Order Promotion System

This project implements an e-commerce order processing system with a focus on applying various promotional discounts. The system is built using Java and Maven, and it utilizes Cucumber for behavior-driven development (BDD).

## Project Overview

The core functionality of this project is to calculate the final price of a customer's order after applying a set of predefined promotions. The system is designed to be flexible and extensible, allowing for the addition of new promotion types in the future.

### Implemented Promotions

The following promotions are currently implemented:

*   **Threshold Discount:** A fixed amount is discounted from the total order price if the subtotal reaches a certain threshold.
*   **Buy-One-Get-One (BOGO):** For items in the "cosmetics" category, the customer receives one free item for each one they purchase.
*   **Double Eleven Discount:** On "Double Eleven" (November 11th), customers receive a 20% discount on every 10 units of the same product.

## Behavior-Driven Development (BDD)

This project follows a strict BDD approach using Cucumber. The expected behavior of the system is defined in `.feature` files, which are written in a human-readable format. These feature files serve as both documentation and executable tests.

The BDD workflow for this project is as follows:

1.  **Write a new scenario:** Define a new scenario in a `.feature` file.
2.  **Run the tests and watch them fail:** The new scenario will fail because the corresponding code has not yet been implemented.
3.  **Write the minimum amount of code to make the test pass:** Implement the necessary logic to make the scenario pass.
4.  **Refactor:** Improve the code while ensuring that all tests continue to pass.

## Open/Closed Principle (OCP)

The `OrderService` class is designed to be open for extension but closed for modification. This means that new promotion types can be added without changing the existing code of the `OrderService`. This is achieved by encapsulating each promotion's logic in its own class.

## How to Build and Run the Tests

This project uses Maven to manage dependencies and build the project. To build the project and run the tests, execute the following command in the root directory of the project:

```bash
mvn clean install
```

This command will compile the source code, run the Cucumber tests, and generate a test report. The test report can be found in the `target/cucumber-reports` directory.
