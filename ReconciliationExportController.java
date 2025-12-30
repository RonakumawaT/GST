package com.example.RK8.Controller;

import com.example.RK8.Service.ReconciliationExportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/export")
public class ReconciliationExportController {

    private final ReconciliationExportService exportService;

    public ReconciliationExportController(ReconciliationExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/reconciliation")
    public ResponseEntity<InputStreamResource> exportReconciliation() {

        ByteArrayInputStream stream =
                exportService.exportReconciliationReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=gst_reconciliation.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }
}
