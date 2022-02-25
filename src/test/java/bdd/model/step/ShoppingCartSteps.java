package bdd.model.step;

import bdd.model.BigDecimalUtil;
import bdd.model.LineItem;
import bdd.model.ShoppingCart;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.*;

public final class ShoppingCartSteps {

    private final ShoppingCart cart = new ShoppingCart();
    private final Map<String, Double> productUnitPrices = new HashMap<>();

    @DataTableType()
    public LineItem convert(Map<String, String> entry) {
        return new LineItem(
                entry.get("name"),
                BigDecimal.valueOf(Double.parseDouble(entry.get("unitPrice"))),
                Integer.parseInt(entry.get("quantity"))
        );
    }

    @Given("AC 0: An empty shopping cart")
    public void ac0_an_empty_shopping_cart() {
    }

    @And("a product {string} with a unit price of _{double}_")
    public void a0_product_with_a_unit_price_of(String theProductName, double theUnitPrice) {
        productUnitPrices.put(theProductName, theUnitPrice);
    }

    @When("The user adds a _{int}_ {string} to the shopping cart")
    public void the_user_adds_a_to_the_shopping_cart(int quantity, String theProductName) {
        addToCart(quantity, theProductName);
    }

    @Then("The shopping cart should have a single line item with _{int}_ {string} with a unit price of _{double}_")
    public void the_shopping_cart_should_a_single_line_item_with_with_a_unit_price_of(int quantity, String productName, double unitPrice) {
        Collection<LineItem> items = cart.getLineItems();
        LineItem item = items.iterator().next();
        assert (items.size() == quantity && item.getQuantity() == quantity &&
                item.getName().equals(productName) &&
                BigDecimalUtil.equal(item.getUnitPrice(), unitPrice)
        );
    }

    @And("the shopping cart total price should equal _{double}_")
    public void the_shopping_cart_s_total_price_should_equal(double totalPrice) {
        assert BigDecimalUtil.equal(cart.getTotalPrice(), totalPrice);
    }

    @Then("all totals should be rounded up to 2 decimal places")
    public void ac0_all_totals_should_be_rounded_up_to_decimal_places() {
        assertTotalPriceToTwoDecimalPoints();
    }

    @Given("AC 1: An empty shopping cart")
    public void ac1_an_empty_shopping_cart() {
    }

    @And("a product, {string} with a unit price of _{double}_")
    public void a1_product_with_a_unit_price_of(String theProductName, double theUnitPrice) {
        productUnitPrices.put(theProductName, theUnitPrice);
    }

    @When("^The user adds items to the shopping cart$")
    public void the_user_adds_to_the_shopping_cart(List<LineItem> items) {
        items.forEach(cart::add);
    }

    @Then("The shopping cart should contain a single _{int}_ line item, because product equality is not instance based")
    public void the_shopping_cart_should_contain_a_single_line_item_because_product_equality_is_not_instance_based(int quantity) {
        Collection<LineItem> items = cart.getLineItems();
        assert (items.size() == quantity);
    }

    @And("The shopping cart should contain _{int}_ Dove Soaps each with a unit price of _{double}_")
    public void the_shopping_cart_should_contain_dove_soaps_each_with_a_unit_price_of(int quantity, double unitPrice) {
        int actualCount = cart.getLineItems().stream().map(LineItem::getQuantity).mapToInt(Integer::intValue).sum();
        boolean allUnitPricesMatched = cart.getLineItems().stream().allMatch(item -> BigDecimalUtil.equal(item.getUnitPrice(), unitPrice));
        assert (actualCount == quantity && allUnitPricesMatched);
    }

    @And("the total price of shopping cart should equal _{double}_")
    public void and_the_shopping_cart_s_total_price_should_equal(double totalPrice) {
        assert BigDecimalUtil.equal(cart.getTotalPrice(), totalPrice);
    }

    @And("All totals should be rounded up to 2 decimal places as described in AC 0")
    public void all_totals_should_be_rounded_up_to_decimal_places_as_described_in_ac() {
        assertTotalPriceToTwoDecimalPoints();
    }

    @Given("AC2: An empty shopping cart")
    public void ac2_an_empty_shopping_cart() {
    }

    @And("a product, {string}, with a unit price of _{double}_")
    public void a_product_with_a_unit_price_of(String productName, double unitPrice) {
        productUnitPrices.put(productName, unitPrice);
    }

    @And("another product {string} with a unit price of _{double}_")
    public void another_product_with_a_unit_price_of(String productName, double unitPrice) {
        productUnitPrices.put(productName, unitPrice);
    }

    @And("a sales tax rate of _{float}%_ applicable to all products equally")
    public void a_sales_tax_rate_of_applicable_to_all_products_equally(float theTaxRate) {
        cart.setTaxRate(theTaxRate % 100);
    }

    @When("^The user adds mixed items to the shopping cart$")
    public void the_user_adds_mxwxwixed_items_to_the_shopping_cart(List<LineItem> items) {
        items.forEach(cart::add);
    }

    @Then("The shopping cart should contain a line item with _{int}_ Dove Soaps each with a unit price of _{double}_")
    public void the_shopping_cart_should_contain_a_line_item_with_dove_soaps_each_with_a_unit_price_of(int quantity, double unitPrice) {
        assert (matchedProduct("Dove Soap", quantity, unitPrice));
    }

    @And("the shopping cart should contain a line item with _{int}_ Axe Deos each with a unit price of _{double}_")
    public void the_shopping_cart_should_contain_a_line_item_with_axe_deos_each_with_a_unit_price_of(int quantity, double unitPrice) {
        assert (matchedProduct("Axe Deo", quantity, unitPrice));
    }

    @And("the total sales tax amount for the shopping cart should equal _{double}_")
    public void the_total_sales_tax_amount_for_the_shopping_cart_should_equal(double expectedTax) {
        float taxRate = 0.125f;
        BigDecimal actualTax = cart.setTaxRate(taxRate).getTotalTax();
        assert BigDecimalUtil.equal(actualTax, expectedTax);
    }

    @And("the shopping cart's total price should equal _{double}_")
    public void a2_the_shopping_cart_s_total_price_should_equal(double totalPrice) {
        assert BigDecimalUtil.equal(cart.getTotalPrice(), totalPrice);
    }

    @And("AC2 All totals should be rounded up to 2 decimal places as described in AC 0")
    public void all_totals_round_up_to_2_decimal_places() {
        assert (assertTotalPriceToTwoDecimalPoints());
    }

    private void addToCart(int quantity, String productName) {
        double unitPrice = productUnitPrices.getOrDefault(productName, 0.0);
        LineItem lineItem = new LineItem(productName, BigDecimal.valueOf(unitPrice), quantity);
        cart.add(lineItem);
    }

    private boolean assertTotalPriceToTwoDecimalPoints() {
        BigDecimal actualTotalPrice = cart.getTotalPrice();
        int expectedPriceScale = 2;
        return actualTotalPrice.scale() == expectedPriceScale;
    }

    private boolean matchedProduct(String name, int quantity, double unitPrice) {
        Optional<LineItem> item = cart.getLineItem(name);
        return item.map(i -> i.getQuantity() == quantity && BigDecimalUtil.equal(i.getUnitPrice(), unitPrice))
                .orElse(false);
    }
}