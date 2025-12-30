package com.example.RK8;

import com.example.RK8.ENUM.RiskLevel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class RiskScoreEngine {

    public RiskLevel calculate(
            BigDecimal itc2B,
            BigDecimal itc3B
    ) {

        if (itc3B.compareTo(BigDecimal.ZERO) == 0) {
            return RiskLevel.HIGH;
        }

        BigDecimal mismatch =
                itc2B.subtract(itc3B).abs();

        BigDecimal percentage =
                mismatch.multiply(BigDecimal.valueOf(100))
                        .divide(itc3B, 2, RoundingMode.HALF_UP);

        if (percentage.compareTo(BigDecimal.ZERO) == 0) {
            return RiskLevel.LOW;
        } else if (percentage.compareTo(BigDecimal.valueOf(10)) <= 0) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.HIGH;
        }
    }
}

