package com.example.RK8.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardSummaryResponse {

    private long totalClients;
    private BigDecimal totalItc2B;
    private BigDecimal totalItc3B;
    private BigDecimal totalMismatch;

    private long lowRiskCount;
    private long mediumRiskCount;
    private long highRiskCount;

    public DashboardSummaryResponse(
            long totalClients,
            BigDecimal totalItc2B,
            BigDecimal totalItc3B,
            BigDecimal totalMismatch,
            long lowRiskCount,
            long mediumRiskCount,
            long highRiskCount
    ) {
        this.totalClients = totalClients;
        this.totalItc2B = totalItc2B;
        this.totalItc3B = totalItc3B;
        this.totalMismatch = totalMismatch;
        this.lowRiskCount = lowRiskCount;
        this.mediumRiskCount = mediumRiskCount;
        this.highRiskCount = highRiskCount;
    }

}

