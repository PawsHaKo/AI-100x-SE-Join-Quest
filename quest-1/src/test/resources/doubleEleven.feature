@order_pricing
Feature: Double Eleven Discount
  Scenario: Exactly 10 identical items get 20% discount
    Given the 'double eleven' is active
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | T-shirt     | 10       | 500       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 5000           | 1000     | 4000        |
    And the customer should receive:
      | productName | quantity |
      | T-shirt     | 10       |

  Scenario: More than 10 identical items, discount applied per 10 units
    Given the 'double eleven' is active
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | T-shirt     | 27       | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 2700           | 400      | 2300        |
    And the customer should receive:
      | productName | quantity |
      | T-shirt     | 27       |

  Scenario: 10 similar but non-identical items do not qualify for discount
    Given the 'double eleven' is active
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | T-shirt 1   | 1        | 100       |
      | T-shirt 2   | 1        | 100       |
      | T-shirt 3   | 1        | 100       |
      | T-shirt 4   | 1        | 100       |
      | T-shirt 5   | 1        | 100       |
      | T-shirt 6   | 1        | 100       |
      | T-shirt 7   | 1        | 100       |
      | T-shirt 8   | 1        | 100       |
      | T-shirt 9   | 1        | 100       |
      | T-shirt 10  | 1        | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 1000           | 0        | 1000        |
    And the customer should receive:
      | productName | quantity |
      | T-shirt 1   | 1        |
      | T-shirt 2   | 1        |
      | T-shirt 3   | 1        |
      | T-shirt 4   | 1        |
      | T-shirt 5   | 1        |
      | T-shirt 6   | 1        |
      | T-shirt 7   | 1        |
      | T-shirt 8   | 1        |
      | T-shirt 9   | 1        |
      | T-shirt 10  | 1        |
