package com.example.RK8.Service;

import com.example.RK8.Gst2BExcelParser;
import com.example.RK8.Gst2BUploadRow;
import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn2B;
import com.example.RK8.Repository.ClientRepository;
import com.example.RK8.Repository.GstReturn2BRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class GstReturn2BService {

    private final ClientRepository clientRepository;
    private final GstReturn2BRepository gstReturn2BRepository;
    private final Gst2BExcelParser parser;

    public GstReturn2BService(
            ClientRepository clientRepository,
            GstReturn2BRepository gstReturn2BRepository,
            Gst2BExcelParser parser
    ) {
        this.clientRepository = clientRepository;
        this.gstReturn2BRepository = gstReturn2BRepository;
        this.parser = parser;
    }

    @Transactional
    public void upload2B(MultipartFile file) {

        List<Gst2BUploadRow> rows = parser.parse(file);

        for (Gst2BUploadRow row : rows) {

            Client client = clientRepository.findByGstin(row.getGstin())
                    .orElseThrow(() -> new RuntimeException(
                            "Client not found: " + row.getGstin()
                    ));

            GSTReturn2B entity = new GSTReturn2B();
            entity.setClient(client);
            entity.setInvoiceNo(row.getInvoiceNo());
            entity.setSupplierGstin(row.getSupplierGstin());
            entity.setItcAmount(row.getItcAmount());

            gstReturn2BRepository.save(entity);
        }
    }
}

