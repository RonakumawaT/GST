package com.example.RK8;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Gst2BExcelParser {

    public List<Gst2BUploadRow> parse(MultipartFile file) {

        List<Gst2BUploadRow> rows = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            iterator.next(); // skip header

            while (iterator.hasNext()) {
                Row row = iterator.next();

                Gst2BUploadRow dto = new Gst2BUploadRow();
                dto.setGstin(row.getCell(0).getStringCellValue().trim());
                dto.setInvoiceNo(row.getCell(1).getStringCellValue().trim());
                dto.setSupplierGstin(row.getCell(2).getStringCellValue().trim());
                dto.setItcAmount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));

                rows.add(dto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse GSTR-2B Excel", e);
        }

        return rows;
    }
}
