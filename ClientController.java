package com.example.RK8.Controller;

import com.example.RK8.Model.Client;
import com.example.RK8.Service.ClientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{gstin}")
    public Client getClient(@PathVariable String gstin) {
        return clientService.getClientByGstin(gstin);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @DeleteMapping
    public String deleteAllClients() {
        clientService.deleteAllClients();
        return "All clients deleted";
    }

    @PostMapping(
            value = "/upload",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<Client> uploadClients(@RequestParam("file") MultipartFile file) {
        return clientService.uploadClients(file);
    }



}