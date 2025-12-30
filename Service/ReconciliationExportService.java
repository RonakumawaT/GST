package com.example.RK8.Service;

import com.example.RK8.Model.ReconciliationHistory;
import com.example.RK8.ReconciliationExcelExporter;
import com.example.RK8.Repository.ReconciliationHistoryRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ReconciliationExportService {

    private final ReconciliationHistoryRepository historyRepository;
    private final ReconciliationExcelExporter excelExporter;

    public ReconciliationExportService(
            ReconciliationHistoryRepository historyRepository,
            ReconciliationExcelExporter excelExporter
    ) {
        this.historyRepository = historyRepository;
        this.excelExporter = excelExporter;
    }

    public ByteArrayInputStream exportReconciliationReport() {
        List<ReconciliationHistory> history =
                historyRepository.findAllByOrderByReconciledAtDesc();

        return excelExporter.export(history);
    }
}
