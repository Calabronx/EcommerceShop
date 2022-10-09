package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<List<Client>> createClient(Client client) {
        List<Client> clientData = new ArrayList<>();
        client.setId(client.getId());
        client.setName(client.getName());
        client.setNextMonthBonus("NOT_BONUS");
        clientData.add(client);
        clientRepository.save(client);
        return new ResponseEntity<>(clientData, HttpStatus.OK);
    }
}
