package bdd.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
//import static org.junit.jupiter.Assertions

public class ShoppingCartTest {

    private ShoppingCart cart;
    @BeforeEach
    public void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    public void addDoveSoap() {
        cart.add(new LineItem(Product.DoveSoap, 3));
        assertEquals(cart.getTotalPrice(), BigDecimal.valueOf(3.0).multiply(BigDecimalUtil.roundHalfUp(2, Product.DoveSoap.getUnitPrice())).stripTrailingZeros());
    }

    @Test
    public void removeDoveSoap() {
        int initQuantity = 3;
        cart.add(new LineItem(Product.DoveSoap, initQuantity));
        cart.remove(Product.DoveSoap, 1);
        assertEquals(cart.getTotalPrice(), BigDecimal.valueOf(initQuantity - 1).multiply(BigDecimalUtil.roundHalfUp(2, Product.DoveSoap.getUnitPrice())).stripTrailingZeros());

        cart.remove(Product.DoveSoap, 10);
        assertTrue(cart.getLineItems().isEmpty());
    }



}
