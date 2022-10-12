package com.factorit.EcommerceShop.controller;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<List<Client>> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping("/getlevel")
    public ResponseEntity<?> getClientsByLevel(@RequestBody Client client) {
        return clientService.getAllClientsByLevel(client);
    }

    @PutMapping("/changelevel/{id}")
    public ResponseEntity<?> changeClientLevel(@RequestBody String clientLevel, @PathVariable Long id) {
        return clientService.changeClientLevel(clientLevel, id);
    }
}