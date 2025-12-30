package com.example.RK8.Model;

import com.example.RK8.ENUM.RiskLevel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reconciliation_history")
@Data
public class ReconciliationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gstin;

    private BigDecimal itc2B;
    private BigDecimal itc3B;
    private BigDecimal mismatch;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RiskLevel risk;

    private LocalDateTime reconciledAt;
}
