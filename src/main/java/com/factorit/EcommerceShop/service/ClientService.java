package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.repository.ClientRepository;
import com.factorit.EcommerceShop.utils.CartEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<List<Client>> createClient(Client client) {
        List<Client> clientData = new ArrayList<>();
        client.setId(client.getId());
        client.setName(client.getName());
        client.setNextMonthBonus(String.valueOf(CartEnum.NOT_BONUS));
        client.setLevel(String.valueOf(CartEnum.DEFAULT_ANY));
        clientData.add(client);
        logger.info("creando cliente");
        clientRepository.save(client);
        return new ResponseEntity<>(clientData, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllClientsByLevel(Client client) {
        //Page<Client> levelPageableClients = clientRepository.findAllLevel(client.getLevel(), Pageable.ofSize(5));
        List<Client> listClients = clientRepository.findAll();
        List<Client> levelList = new ArrayList<>();

        if (listClients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No hay clientes registrados");
        }

        if (client.getLevel().equals(String.valueOf(CartEnum.COMUN)) || client.getLevel().equals(String.valueOf(CartEnum.PROMOCIONABLE))
                || client.getLevel().equals(String.valueOf(CartEnum.VIP))) {
            for (int i = 0; i < listClients.size(); i++) {
                Client clientObj = new Client();
                String levelCompare = listClients.get(i).getLevel();
                String levelRequest = client.getLevel();
                if (levelCompare.equals(levelRequest)) {
                    Long clientId = listClients.get(i).getId();
                    String clientName = listClients.get(i).getName();
                    String bonusClient = listClients.get(i).getNextMonthBonus();
                    String levelClients = listClients.get(i).getLevel();
                    clientObj.setName(clientName);
                    clientObj.setId(clientId);
                    clientObj.setLevel(levelClients);
                    clientObj.setNextMonthBonus(bonusClient);
                    levelList.add(clientObj);
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Tipo de cliente ingresado no es correcto. Permitidos: COMUN - PROMOCIONABLE - VIP");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(levelList);
    }

    public ResponseEntity<?> changeClientLevel(String levelChange, Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Cliente no encontrado");
        }
        Client clientUpgrade = clientRepository.getById(id);

        if (levelChange.equals(String.valueOf(CartEnum.COMUN)) || levelChange.equals(String.valueOf(CartEnum.PROMOCIONABLE))
        ) {
            clientUpgrade.setLevel(levelChange);
            clientRepository.save(clientUpgrade);

        } else if (levelChange.equals(String.valueOf(CartEnum.VIP))) {
            clientUpgrade.setLevel(levelChange);
            clientUpgrade.setVipAdquireDate(LocalDateTime.now());
            clientRepository.save(clientUpgrade);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientUpgrade);
    }
}
