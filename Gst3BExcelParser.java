package com.example.RK8;

import com.example.RK8.DTO.Gst3BUploadRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class Gst3BExcelParser {

    public List<Gst3BUploadRow> parse(MultipartFile file) {

        List<Gst3BUploadRow> rows = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell gstinCell = row.getCell(0);
                Cell itcCell = row.getCell(1);

                // skip completely empty rows
                if (gstinCell == null || itcCell == null) {
                    continue;
                }

                String gstin = gstinCell.getStringCellValue()
                        .trim()
                        .toUpperCase();

                if (gstin.isEmpty()) continue;

                BigDecimal itcClaimed;

                if (itcCell.getCellType() == CellType.NUMERIC) {
                    itcClaimed = BigDecimal.valueOf(itcCell.getNumericCellValue());
                } else if (itcCell.getCellType() == CellType.STRING) {
                    itcClaimed = new BigDecimal(itcCell.getStringCellValue().trim());
                } else {
                    throw new RuntimeException(
                            "Invalid ITC value at row " + (i + 1)
                    );
                }

                Gst3BUploadRow r = new Gst3BUploadRow();
                r.setGstin(gstin);
                r.setItcClaimed(itcClaimed);

                rows.add(r);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse GSTR-3B Excel", e);
        }

        return rows;
    }
}
