package com.example.RK8.Service;

import com.example.RK8.ClientExcelParser;
import com.example.RK8.DTO.ClientUploadRow;
import com.example.RK8.Model.Client;
import com.example.RK8.Repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientExcelParser clientExcelParser;

    public ClientService(ClientRepository clientRepository,ClientExcelParser clientExcelParser) {
        this.clientExcelParser = clientExcelParser;
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public List<Client> uploadClients(MultipartFile file) {

        List<ClientUploadRow> rows = clientExcelParser.parse(file);
        List<Client> savedClients = new ArrayList<>();

        for (ClientUploadRow row : rows) {

            if (clientRepository.findByGstin(row.getGstin()).isPresent()) {
                continue; // skip duplicates
            }

            Client client = new Client();
            client.setGstin(row.getGstin());
            client.setClientName(row.getClientName());
            client.setAssignedTo(row.getAssignedTo());

            savedClients.add(clientRepository.save(client));
        }

        return savedClients;
    }


    public Client getClientByGstin(String gstin) {
        return clientRepository.findByGstin(gstin)
                .orElseThrow(() -> new RuntimeException("Client not found: " + gstin));
    }

    public Client createClient(Client client) {
        clientRepository.findByGstin(client.getGstin())
                .ifPresent(c -> {
                    throw new RuntimeException(
                            "Client already exists with GSTIN: " + client.getGstin()
                    );
                });
        return clientRepository.save(client);
    }

    public void deleteAllClients() {
        clientRepository.deleteAll();
    }
}
