package bdd.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class LineItem {
    private final String name;
    private final BigDecimal unitPrice;
    private final int quantity;

    public LineItem(String name, BigDecimal unitPrice, int quantity) {
        Objects.requireNonNull(name);
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }
}
