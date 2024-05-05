package br.com.pbcompass.demoparkapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

    private static final double FIRST_15_MINUTES = 5.00;
    private static final double FIRST_60_MINUTES = 9.25;
    private static final double ADD_15_MINUTES = 1.75;
    private static final double DISCOUNT_PERCENTAGE = 0.30;

    public static String generateInvoice() {
        LocalDateTime dateTime = LocalDateTime.now();
        String invoiceNumber = dateTime.toString().substring(0, 19);
        invoiceNumber = invoiceNumber.replace("-", "").replace(":", "").replace("T", "-");
        return invoiceNumber;
    }

    public static BigDecimal calculateCost(LocalDateTime checkin, LocalDateTime checkoutTime) {
        long minutes = checkin.until(checkoutTime, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total = FIRST_15_MINUTES;
        } else if (minutes <= 60) {
            total = FIRST_60_MINUTES;
        } else {
            long additionalMinutes = minutes - 60;
            Double totalParts = ((double) additionalMinutes / 15);
            if (totalParts > totalParts.intValue()) { // 4.66 > 4
                total += FIRST_60_MINUTES + (ADD_15_MINUTES * (totalParts.intValue() + 1));
            } else { // 4.0
                total += FIRST_60_MINUTES + (ADD_15_MINUTES * totalParts.intValue());
            }
        }
        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculateDiscount(BigDecimal cost, long checkouts) {
        BigDecimal discount = ((checkouts > 0) && (checkouts % 10 == 0))
                ? cost.multiply(new BigDecimal(DISCOUNT_PERCENTAGE))
                : new BigDecimal(0);
        return discount.setScale(2, RoundingMode.HALF_EVEN);
    }
}
