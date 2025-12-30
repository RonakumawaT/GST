package com.example.RK8.Service;

import com.example.RK8.DTO.Gst3BUploadRow;
import com.example.RK8.Gst3BExcelParser;
import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn3B;
import com.example.RK8.Repository.ClientRepository;
import com.example.RK8.Repository.GstReturn3BRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class GstReturn3BService {

    private final ClientRepository clientRepository;
    private final GstReturn3BRepository gstReturn3BRepository;
    private final Gst3BExcelParser parser;

    public GstReturn3BService(
            ClientRepository clientRepository,
            GstReturn3BRepository gstReturn3BRepository,
            Gst3BExcelParser parser
    ) {
        this.clientRepository = clientRepository;
        this.gstReturn3BRepository = gstReturn3BRepository;
        this.parser = parser;
    }

    @Transactional
    public void upload3B(MultipartFile file) {

        List<Gst3BUploadRow> rows = (List<Gst3BUploadRow>) parser.parse(file);

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

}

