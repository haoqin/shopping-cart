package bdd.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    public static BigDecimal roundHalfUp(int scale, BigDecimal amount) {
        return amount.setScale(scale, RoundingMode.HALF_UP);
    }

    public static boolean equal(BigDecimal bigDecimal, double another) {
        BigDecimal anotherBigDecimal = BigDecimal.valueOf(another);
        return bigDecimal.equals(anotherBigDecimal.setScale(bigDecimal.scale()));
    }
}
