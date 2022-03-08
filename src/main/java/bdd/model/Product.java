package bdd.model;

import java.math.BigDecimal;

public class Product {
    private final BigDecimal unitPrice;
    private final String name;

    public static Product DoveSoap = new Product("Dove Soap", BigDecimal.valueOf(39.99f));
    public static Product AxeDeo = new Product("Axe Deo", BigDecimal.valueOf(99.99f));

    public Product(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }
    public  String getName() {
        return this.name;
    }

}
