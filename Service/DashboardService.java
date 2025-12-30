package com.example.RK8.Service;

import com.example.RK8.DTO.DashboardSummaryResponse;
import com.example.RK8.ENUM.RiskLevel;
import com.example.RK8.Repository.ReconciliationHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ReconciliationHistoryRepository historyRepository;

    public DashboardService(ReconciliationHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public DashboardSummaryResponse getSummary() {

        return new DashboardSummaryResponse(
                historyRepository.countDistinctClients(),
                historyRepository.sumItc2B(),
                historyRepository.sumItc3B(),
                historyRepository.sumMismatch(),
                historyRepository.countByRisk(RiskLevel.LOW),
                historyRepository.countByRisk(RiskLevel.MEDIUM),
                historyRepository.countByRisk(RiskLevel.HIGH)
        );
    }
}

