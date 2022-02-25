@Cucumber
Feature: Testing for shopping cart
  @SingleItem
  Scenario: AC0: Adding a single product.
    Given AC 0: An empty shopping cart
    And   a product "<_Dove Soap_>" with a unit price of _39.99_
    When The user adds a _1_ "<_Dove Soap_>" to the shopping cart
    Then The shopping cart should have a single line item with _1_ "<_Dove Soap_>" with a unit price of _39.99_
    And the shopping cart's total price should equal _39.99_
    And all totals should be rounded up to 2 decimal places

  @DoubleItems
  Scenario: AC1: Adding many products.
    Given AC 1: An empty shopping cart
    And a product, "<_Dove Soap_>" with a unit price of _39.99_
    When The user adds items to the shopping cart
      |	name	  |	unitPrice|quantity|
      |	Dove Soap |	39.99	 |5	      |
      |	Dove Soap |	39.99	 |3       |
    Then The shopping cart should contain a single _1_ line item, because product equality is not instance based
    And The shopping cart should contain _8_ Dove Soaps each with a unit price of _39.99_
    And the shopping cart's total price should equal _319.92_
    And All totals should be rounded up to 2 decimal places as described in AC 0

  @MixedItems
  Scenario: AC 2: Calculate tax rate with many products.
    Given AC2: An empty shopping cart
    And a product, "<_Dove Soap_>", with a unit price of _39.99_
    And another product "<_Axe Deo_>" with a unit price of _99.99_
    And a sales tax rate of _12.5%_ applicable to all products equally
    When The user adds mixed items to the shopping cart
      |	name		|unitPrice|quantity|
      |	Dove Soap 	|39.99	  |2       |
      |	Axe Deo 	|99.99    |2       |
    Then The shopping cart should contain a line item with _2_ Dove Soaps each with a unit price of _39.99_
    And the shopping cart should contain a line item with _2_ Axe Deos each with a unit price of _99.99_
    And the total sales tax amount for the shopping cart should equal _35.00_
    And the shopping cart's total price should equal _314.96_
    And AC2 All totals should be rounded up to 2 decimal places as described in AC 0

