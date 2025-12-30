package com.example.RK8;

import com.example.RK8.Model.ReconciliationHistory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ReconciliationExcelExporter {

    public ByteArrayInputStream export(List<ReconciliationHistory> history) {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("GST Reconciliation");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("GSTIN");
            header.createCell(1).setCellValue("ITC 2B");
            header.createCell(2).setCellValue("ITC 3B");
            header.createCell(3).setCellValue("Mismatch");
            header.createCell(4).setCellValue("Risk");
            header.createCell(5).setCellValue("Reconciled At");

            int rowIdx = 1;

            for (ReconciliationHistory r : history) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getGstin());
                row.createCell(1).setCellValue(r.getItc2B().doubleValue());
                row.createCell(2).setCellValue(r.getItc3B().doubleValue());
                row.createCell(3).setCellValue(r.getMismatch().doubleValue());
                row.createCell(4).setCellValue(r.getRisk().name());
                row.createCell(5).setCellValue(r.getReconciledAt().toString());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel", e);
        }
    }
}

