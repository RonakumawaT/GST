package com.example.RK8.Controller;

import com.example.RK8.Model.Client;
import com.example.RK8.Model.ReconciliationHistory;
import com.example.RK8.Repository.ReconciliationHistoryRepository;
import com.example.RK8.Service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class ReconciliationHistoryController {

    private final ClientService clientService;
    private final ReconciliationHistoryRepository historyRepository;

    public ReconciliationHistoryController(
            ClientService clientService,
            ReconciliationHistoryRepository historyRepository
    ) {
        this.clientService = clientService;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/{gstin}")
    public List<ReconciliationHistory> getHistory(@PathVariable String gstin) {
        return historyRepository.findByGstinOrderByReconciledAtDesc(gstin);
    }

}

