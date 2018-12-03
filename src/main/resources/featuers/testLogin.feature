Feature: login
  As a user
  I can add and view income and expense
  so that I know how much money I have

  Scenario: Add income
    Given A user has 3000 for income
    When I add income amount 2000
    Then I have total income 5000

  Scenario: Spend a money less than income
    Given A user has 3000 for income
    When I spend money amount 500
    Then I have total income 2500
    And My expense is 500

  Scenario: Spend a money more than income
    Given A user has 3000 for income
    When I spend money amount 3200
    Then I have total income 0
    And My expense is 3000

  Scenario: Check currently income
    Given A user has 3000 for income
    When I want to check my current income is that 3000
    Then The system shows my current income amount 3000

  Scenario: Check currently expense
    Given A user has 3000 for income
    When I spend money amount 500
    Then The system shows my current expense amount 500