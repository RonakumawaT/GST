package com.example.RK8.Service;

import com.example.RK8.*;
import com.example.RK8.DTO.Gst3BUploadRow;
import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn2B;
import com.example.RK8.Model.GSTReturn3B;
import com.example.RK8.Repository.ClientRepository;
import com.example.RK8.Repository.GstReturn2BRepository;
import com.example.RK8.Repository.GstReturn3BRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UploadService {

    private final Gst2BExcelParser gst2BExcelParser;
    private final Gst3BExcelParser gst3BExcelParser;
    private final GstReturn3BRepository gstReturn3BRepository;
    private final GstReturn2BRepository gstReturn2BRepository;
    private final ClientRepository clientRepository;

    public UploadService(
            Gst2BExcelParser gst2BExcelParser,
            Gst3BExcelParser gst3BExcelParser,
            GstReturn3BRepository gstReturn3BRepository,
            GstReturn2BRepository gstReturn2BRepository,
            ClientRepository clientRepository
    ) {
        this.gst2BExcelParser = gst2BExcelParser;
        this.gst3BExcelParser = gst3BExcelParser;
        this.gstReturn3BRepository = gstReturn3BRepository;
        this.gstReturn2BRepository = gstReturn2BRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void upload3B(MultipartFile file) {

        List<Gst3BUploadRow> rows = (List<Gst3BUploadRow>) gst3BExcelParser.parse(file);

        for (Gst3BUploadRow row : rows) {

            Client client = clientRepository.findByGstin(row.getGstin())
                    .orElseThrow(() ->
                            new RuntimeException("Client not found: " + row.getGstin())
                    );

            GSTReturn3B entity = new GSTReturn3B();
            entity.setClient(client);
            entity.setItcClaimed(row.getItcClaimed());

            gstReturn3BRepository.save(entity);
        }
    }



    @Transactional
    public void upload2B(MultipartFile file) {

        List<Gst2BUploadRow> rows = gst2BExcelParser.parse(file);

        // 1️⃣ Collect all GSTINs from Excel
        Set<String> gstins = rows.stream()
                .map(Gst2BUploadRow::getGstin)
                .collect(Collectors.toSet());

        // 2️⃣ Load all clients in ONE query
        Map<String, Client> clientMap =
                clientRepository.findByGstinIn(gstins)
                        .stream()
                        .collect(Collectors.toMap(Client::getGstin, c -> c));

        // 3️⃣ Validate missing clients
        List<String> missing = gstins.stream()
                .filter(g -> !clientMap.containsKey(g))
                .toList();

        if (!missing.isEmpty()) {
            throw new RuntimeException("Clients not found: " + missing);
        }

        // 4️⃣ Save 2B rows
        for (Gst2BUploadRow row : rows) {

            Client client = clientMap.get(row.getGstin());

            GSTReturn2B entity = new GSTReturn2B();
            entity.setClient(client);
            entity.setInvoiceNo(row.getInvoiceNo());
            entity.setSupplierGstin(row.getSupplierGstin());
            entity.setItcAmount(row.getItcAmount());

            gstReturn2BRepository.save(entity);
        }
    }

}
