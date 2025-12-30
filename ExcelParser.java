package com.example.RK8;

import com.example.RK8.DTO.Gst3BUploadRow;
import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn2B;
import com.example.RK8.Model.GSTReturn3B;
import com.example.RK8.Repository.ClientRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class ExcelParser {

    private final ClientRepository clientRepository;

    public ExcelParser(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // ======================
    // GSTR-3B Excel Upload
    // ======================
    public List<Gst3BUploadRow> parse3B(MultipartFile file) {

        List<Gst3BUploadRow> rows = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Gst3BUploadRow r = new Gst3BUploadRow();
                r.setGstin(row.getCell(0).getStringCellValue().trim().toUpperCase());
                r.setItcClaimed(
                        BigDecimal.valueOf(row.getCell(1).getNumericCellValue())
                );

                rows.add(r);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse 3B Excel", e);
        }

        return rows;
    }


    // ======================
// GSTR-2B Excel Upload
// ======================
    public List<GSTReturn2B> parse2B(MultipartFile file) {

        List<GSTReturn2B> records = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String gstin = row.getCell(0).getStringCellValue().trim();
                String supplierGstin = row.getCell(1).getStringCellValue().trim();
                String invoiceNo = row.getCell(2).getStringCellValue().trim();
                double itc = row.getCell(3).getNumericCellValue();

                Client client = clientRepository.findByGstin(gstin).orElse(null);

                // ⚠️ NO CRASH — skip bad rows
                if (client == null) {
                    System.out.println("Client not found for 2B: " + gstin);
                    continue;
                }

                GSTReturn2B r = new GSTReturn2B();
                r.setClient(client);
                r.setSupplierGstin(supplierGstin);
                r.setInvoiceNo(invoiceNo);
                r.setItcAmount(BigDecimal.valueOf(itc));

                records.add(r);
            }

        } catch (Exception e) {
            throw new RuntimeException("Invalid GSTR-2B Excel file");
        }

        return records;
    }

}
