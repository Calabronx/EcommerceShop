package com.factorit.EcommerceShop;

import com.factorit.EcommerceShop.controller.ClientController;
import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.repository.ClientRepository;
import com.factorit.EcommerceShop.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = EcommerceShopApplication.class)
@AutoConfigureMockMvc
@Service
public class ClientTest {

    @Autowired
    public MockMvc mockMvc;

    @InjectMocks
    ClientRepository clientRepository = mock(ClientRepository.class);

    @InjectMocks
    private ClientController controller = mock(ClientController.class);

    @MockBean
    private ClientService clientService = mock(ClientService.class);

    @Test
    public void shouldReturnClientsList() throws Exception {

        List<Client> listClients = clientRepository.findAll();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(
                listClients,
                header,
                HttpStatus.OK
        );

        when(clientRepository.findAll()).thenReturn(listClients);
    }
}