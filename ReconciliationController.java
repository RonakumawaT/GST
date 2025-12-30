package com.example.RK8.Controller;

import com.example.RK8.Model.Client;
import com.example.RK8.Model.ReconciliationResult;
import com.example.RK8.Service.ClientService;
import com.example.RK8.Service.ReconciliationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reconcile")
public class ReconciliationController {

    private final ClientService clientService;
    private final ReconciliationService reconciliationService;

    public ReconciliationController(
            ClientService clientService,
            ReconciliationService reconciliationService
    ) {
        this.clientService = clientService;
        this.reconciliationService = reconciliationService;
    }

    @GetMapping("/{gstin}")
    public ReconciliationResult reconcile(@PathVariable String gstin) {

        Client client = clientService.getClientByGstin(gstin);
        return reconciliationService.reconcile(client);
    }
}
