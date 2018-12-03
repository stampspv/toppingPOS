$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("pos/TestNewOrder.feature");
formatter.feature({
  "line": 1,
  "name": "TestNewOrder",
  "description": "for test new order",
  "id": "testneworder",
  "keyword": "Feature"
});
formatter.scenario({
  "comments": [
    {
      "line": 4,
      "value": "#  Scenario: Customer New Order #1"
    },
    {
      "line": 5,
      "value": "#    Given Customer want main : 1"
    },
    {
      "line": 6,
      "value": "#    And Customer want option : 2"
    },
    {
      "line": 7,
      "value": "#    And Customer want topping : 2, 3, 4"
    },
    {
      "line": 8,
      "value": "#    And Price is 75.0"
    },
    {
      "line": 9,
      "value": "#    When Customer confirm"
    },
    {
      "line": 10,
      "value": "#    Then Order has record to database"
    },
    {
      "line": 11,
      "value": "#"
    },
    {
      "line": 12,
      "value": "#  Scenario: Customer New Order #2"
    },
    {
      "line": 13,
      "value": "#    Given Customer want main : 4"
    },
    {
      "line": 14,
      "value": "#    And Customer want option : 4"
    },
    {
      "line": 15,
      "value": "#    And Customer want topping : 4, 3, 2, 1"
    },
    {
      "line": 16,
      "value": "#    And Price is 85.0"
    },
    {
      "line": 17,
      "value": "#    When Customer confirm"
    },
    {
      "line": 18,
      "value": "#    Then Order has record to database"
    },
    {
      "line": 19,
      "value": "#"
    },
    {
      "line": 20,
      "value": "#  Scenario: Customer New Order #3"
    },
    {
      "line": 21,
      "value": "#    Given Customer want main : 2"
    },
    {
      "line": 22,
      "value": "#    And Customer want option : 2"
    },
    {
      "line": 23,
      "value": "#    And Customer want topping : 2"
    },
    {
      "line": 24,
      "value": "#    And Price is 55.0"
    },
    {
      "line": 25,
      "value": "#    When Customer confirm"
    },
    {
      "line": 26,
      "value": "#    Then Order has record to database"
    }
  ],
  "line": 28,
  "name": "Customer New Order But price is less than 0",
  "description": "",
  "id": "testneworder;customer-new-order-but-price-is-less-than-0",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 29,
  "name": "Customer want main : 4",
  "keyword": "Given "
});
formatter.step({
  "line": 30,
  "name": "Customer want option : 3",
  "keyword": "And "
});
formatter.step({
  "line": 31,
  "name": "Customer want topping : 2",
  "keyword": "And "
});
formatter.step({
  "line": 32,
  "name": "Price is -100",
  "keyword": "And "
});
formatter.step({
  "line": 33,
  "name": "Customer confirm",
  "keyword": "When "
});
formatter.step({
  "line": 34,
  "name": "Order has not record to database",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "4",
      "offset": 21
    }
  ],
  "location": "StepDefIncome.customer_want_main(int)"
});
formatter.result({
  "duration": 118700123,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "3",
      "offset": 23
    }
  ],
  "location": "StepDefIncome.customer_option(Integer)"
});
formatter.result({
  "duration": 319931,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "2",
      "offset": 24
    }
  ],
  "location": "StepDefIncome.customer_topping(String)"
});
formatter.result({
  "duration": 3686182,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "-100",
      "offset": 9
    }
  ],
  "location": "StepDefIncome.price_is(Float)"
});
formatter.result({
  "duration": 119454,
  "status": "passed"
});
formatter.match({
  "location": "StepDefIncome.customer_confirm()"
});
formatter.result({
  "duration": 125630555,
  "status": "passed"
});
formatter.match({
  "location": "StepDefIncome.record_no_confirm()"
});
formatter.result({
  "duration": 4267612,
  "status": "passed"
});
formatter.scenario({
  "line": 36,
  "name": "Customer New Order But main product does not exist",
  "description": "",
  "id": "testneworder;customer-new-order-but-main-product-does-not-exist",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 37,
  "name": "Customer want main : 100",
  "keyword": "Given "
});
formatter.step({
  "line": 38,
  "name": "Customer want option : 30",
  "keyword": "And "
});
formatter.step({
  "line": 39,
  "name": "Customer want topping : 2,5,7,8",
  "keyword": "And "
});
formatter.step({
  "line": 40,
  "name": "Price is 100",
  "keyword": "And "
});
formatter.step({
  "line": 41,
  "name": "Customer confirm",
  "keyword": "When "
});
formatter.step({
  "line": 42,
  "name": "Order has not record to database",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "100",
      "offset": 21
    }
  ],
  "location": "StepDefIncome.customer_want_main(int)"
});
formatter.result({
  "duration": 175892,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "30",
      "offset": 23
    }
  ],
  "location": "StepDefIncome.customer_option(Integer)"
});
formatter.result({
  "duration": 57214,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "2,5,7,8",
      "offset": 24
    }
  ],
  "location": "StepDefIncome.customer_topping(String)"
});
formatter.result({
  "duration": 68377,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "100",
      "offset": 9
    }
  ],
  "location": "StepDefIncome.price_is(Float)"
});
formatter.result({
  "duration": 79824,
  "status": "passed"
});
formatter.match({
  "location": "StepDefIncome.customer_confirm()"
});
formatter.result({
  "duration": 901756,
  "status": "passed"
});
formatter.match({
  "location": "StepDefIncome.record_no_confirm()"
});
formatter.result({
  "duration": 37425,
  "status": "passed"
});
});