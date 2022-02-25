package bdd.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BigDecimalUtilSpec {
    private final int scale = 2;
    @Test
    void testRoundHalfUp() {
        Assertions.assertEquals(
                BigDecimalUtil.roundHalfUp(scale, BigDecimal.valueOf(1.234)),
                BigDecimal.valueOf(Double.parseDouble("1.23")));
        Assertions.assertEquals(
                BigDecimalUtil.roundHalfUp(scale, BigDecimal.valueOf(1.235)),
                BigDecimal.valueOf(Double.parseDouble("1.24")));
    }

    @Test
    void testEqual() {
        Assertions.assertTrue(BigDecimalUtil.equal(BigDecimal.valueOf(148844882.78), 148844882.78));
    }
}
