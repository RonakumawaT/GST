package com.example.RK8;

import com.example.RK8.DTO.ClientUploadRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ClientExcelParser {

    public List<ClientUploadRow> parse(MultipartFile file) {

        List<ClientUploadRow> rows = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            iterator.next(); // skip header row

            while (iterator.hasNext()) {
                Row row = iterator.next();

                ClientUploadRow dto = new ClientUploadRow();
                dto.setGstin(row.getCell(0).getStringCellValue().trim());
                dto.setClientName(row.getCell(1).getStringCellValue().trim());
                dto.setAssignedTo(row.getCell(2).getStringCellValue().trim());

                rows.add(dto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }

        return rows;
    }
}
