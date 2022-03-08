package bdd.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class LineItem {
    private final Product product;
    private final int quantity;

    public LineItem(Product product, int quantity) {
        Objects.requireNonNull(product);
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public BigDecimal getLineItemPrice() {
        return BigDecimal.valueOf(this.quantity).multiply(product.getUnitPrice());
    }
    public int getQuantity() {
        return this.quantity;
    }
}
