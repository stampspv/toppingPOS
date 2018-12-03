Feature: TestNewOrder
  for test new order

  Scenario: Customer New Order #1
    Given Customer want main : 1
    And Customer want option : 2
    And Customer want topping : 2, 3, 4
    And Price is 75.0
    When Customer confirm
    Then Order has record to database

  Scenario: Customer New Order #2
    Given Customer want main : 4
    And Customer want option : 4
    And Customer want topping : 4, 3, 2, 1
    And Price is 85.0
    When Customer confirm
    Then Order has record to database

  Scenario: Customer New Order #3
    Given Customer want main : 2
    And Customer want option : 2
    And Customer want topping : 2
    And Price is 55.0
    When Customer confirm
    Then Order has record to database

  Scenario: Customer New Order But price is less than 0
    Given Customer want main : 4
    And Customer want option : 3
    And Customer want topping : 2
    And Price is -100
    When Customer confirm
    Then Order has not record to database

  Scenario: Customer New Order But main product does not exist
    Given Customer want main : 100
    And Customer want option : 30
    And Customer want topping : 2,5,7,8
    And Price is 100
    When Customer confirm
    Then Order has not record to database