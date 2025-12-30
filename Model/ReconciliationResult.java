package com.example.RK8.Model;

import com.example.RK8.ENUM.RiskLevel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReconciliationResult {

    private String gstin;
    private BigDecimal itcAsPer2B;
    private BigDecimal itcAsPer3B;
    private BigDecimal mismatch;
    private RiskLevel riskLevel;

    // ---------- constructor ----------
    public ReconciliationResult(
            String gstin,
            BigDecimal itcAsPer2B,
            BigDecimal itcAsPer3B,
            BigDecimal mismatch,
            RiskLevel risk
    ) {
        this.gstin = gstin;
        this.itcAsPer2B = itcAsPer2B;
        this.itcAsPer3B = itcAsPer3B;
        this.mismatch = mismatch;
        this.riskLevel = risk;
    }

    // ---------- risk calculation ----------
    private String calculateRisk() {

        BigDecimal absMismatch = mismatch.abs();

        if (absMismatch.compareTo(new BigDecimal("1000")) > 0) {
            return "HIGH";
        }
        if (absMismatch.compareTo(BigDecimal.ZERO) > 0) {
            return "MEDIUM";
        }
        return "LOW";
    }

}
