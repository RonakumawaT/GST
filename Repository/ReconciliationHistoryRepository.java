package com.example.RK8.Repository;

import com.example.RK8.ENUM.RiskLevel;

import com.example.RK8.Model.ReconciliationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ReconciliationHistoryRepository
        extends JpaRepository<ReconciliationHistory, Long> {

    List<ReconciliationHistory>
    findByGstinOrderByReconciledAtDesc(String gstin);

    long countByRisk(RiskLevel risk);

    @Query("select count(distinct r.gstin) from ReconciliationHistory r")
    long countDistinctClients();

    @Query("select coalesce(sum(r.itc2B), 0) from ReconciliationHistory r")
    BigDecimal sumItc2B();

    @Query("select coalesce(sum(r.itc3B), 0) from ReconciliationHistory r")
    BigDecimal sumItc3B();

    @Query("select coalesce(sum(r.mismatch), 0) from ReconciliationHistory r")
    BigDecimal sumMismatch();

    List<ReconciliationHistory> findAllByOrderByReconciledAtDesc();

}



