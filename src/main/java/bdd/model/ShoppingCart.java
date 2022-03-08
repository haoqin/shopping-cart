package bdd.model;

import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ShoppingCart {
    private final int scale = 2;
    private ConcurrentHashMap<Product, LineItem> items = new ConcurrentHashMap<>();
    private final AtomicReference<Float> taxRate = new AtomicReference<>(0.0f);

    public ShoppingCart setTaxRate(float theTaxRate) {
        taxRate.set(theTaxRate);
        return this;
    }

    public void add(LineItem item) {
        Objects.requireNonNull(item);
        Product oldProduct = item.getProduct();

        // Mutually exclusive atomic operations.
        items.computeIfPresent(oldProduct, (k, oldItem) -> new LineItem(oldProduct, oldItem.getQuantity() + item.getQuantity()));
        items.putIfAbsent(item.getProduct(), item);
    }

    public synchronized void remove(Product oldProduct, int quantity) {
        Objects.requireNonNull(oldProduct);
        items.computeIfPresent(oldProduct, (k, oldItem) -> {
            return new LineItem(oldProduct, oldItem.getQuantity() - quantity);
        });

        for (Product p : items.keySet()) {
            if (items.get(p).getQuantity() <= 0) {
                items.remove(p);
            }
        }
    }

    public Collection<LineItem> getLineItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public Optional<LineItem> getLineItem(String name) {
        return Optional.ofNullable(items.get(name));
    }

    private BigDecimal getTotalPriceWithoutTax() {
        return items.values().stream().map(item -> item.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalTax() {
        BigDecimal totalPriceBeforeTax = getTotalPriceWithoutTax();
        return BigDecimalUtil.roundHalfUp(scale, totalPriceBeforeTax.multiply(BigDecimal.valueOf(taxRate.get())));
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPriceBeforeTax = getTotalPriceWithoutTax();
        float multiplier = 1.0f + taxRate.get();
        BigDecimal totalPrice = totalPriceBeforeTax.multiply(BigDecimal.valueOf(multiplier)).stripTrailingZeros();
        return BigDecimalUtil.roundHalfUp(scale, totalPrice);
    }

}
