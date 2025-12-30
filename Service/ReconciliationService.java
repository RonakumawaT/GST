package com.example.RK8.Service;

import com.example.RK8.ENUM.RiskLevel;
import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn3B;
import com.example.RK8.Model.ReconciliationHistory;
import com.example.RK8.Model.ReconciliationResult;
import com.example.RK8.Repository.GstReturn2BRepository;
import com.example.RK8.Repository.GstReturn3BRepository;
import com.example.RK8.Repository.ReconciliationHistoryRepository;
import com.example.RK8.RiskScoreEngine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ReconciliationService {

    private final GstReturn2BRepository gstReturn2BRepository;
    private final GstReturn3BRepository gstReturn3BRepository;
    private final ReconciliationHistoryRepository historyRepository;
    private final RiskScoreEngine riskScoreEngine;

    public ReconciliationService(
            GstReturn2BRepository gstReturn2BRepository,
            GstReturn3BRepository gstReturn3BRepository,
            ReconciliationHistoryRepository historyRepository, RiskScoreEngine riskScoreEngine
    ) {
        this.gstReturn2BRepository = gstReturn2BRepository;
        this.gstReturn3BRepository = gstReturn3BRepository;
        this.historyRepository = historyRepository;
        this.riskScoreEngine = riskScoreEngine;
    }

    public ReconciliationResult reconcile(Client client) {

        BigDecimal itc2B =
                gstReturn2BRepository.sumItcByClient(client);
        if (itc2B == null) itc2B = BigDecimal.ZERO;

        GSTReturn3B r3b =
                gstReturn3BRepository.findByClient(client).orElse(null);

        BigDecimal itc3B =
                (r3b == null) ? BigDecimal.ZERO : r3b.getItcClaimed();

        BigDecimal mismatch = itc2B.subtract(itc3B);

        RiskLevel risk = riskScoreEngine.calculate(itc2B, itc3B);

        // ✅ Build result
        ReconciliationResult result =
                new ReconciliationResult(
                        client.getGstin(),
                        itc2B,
                        itc3B,
                        mismatch,
                        risk
                );

        // ✅ SAVE HISTORY (NEW)
        ReconciliationHistory history = new ReconciliationHistory();
        history.setGstin(client.getGstin());
        history.setItc2B(itc2B);
        history.setItc3B(itc3B);
        history.setMismatch(mismatch);
        history.setReconciledAt(LocalDateTime.now());
        history.setRisk(risk);
        historyRepository.save(history);

        return result;
    }
}

