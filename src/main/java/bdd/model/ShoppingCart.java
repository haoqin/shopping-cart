package bdd.model;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class ShoppingCart {
    private final int scale = 2;
    private final ConcurrentHashMap<String, LineItem> items = new ConcurrentHashMap<>();
    private final AtomicReference<Float> taxRate = new AtomicReference<>(0.0f);

    public ShoppingCart setTaxRate(float theTaxRate) {
        taxRate.set(theTaxRate);
        return this;
    }

    public void add(LineItem item) {
        Objects.requireNonNull(item);
        String name = item.getName();

        // Mutually exclusive atomic operations.
        items.computeIfPresent(name, (k, oldItem) -> new LineItem(name, oldItem.getUnitPrice(), oldItem.getQuantity() + item.getQuantity()));
        items.putIfAbsent(name, item);
    }

    public Collection<LineItem> getLineItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public Optional<LineItem> getLineItem(String name) {
        return Optional.ofNullable(items.get(name));
    }

    private BigDecimal getTotalPriceWithoutTax() {
        return items.values().stream().map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
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
