package com.factorit.EcommerceShop;
import com.factorit.EcommerceShop.controller.ProductController;
import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.service.ProductService;
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
public class ProductTest {

    @Autowired
    public MockMvc mockMvc;

    @InjectMocks
    ProductRepository productRepository = mock(ProductRepository.class);

    @InjectMocks
    private ProductController controller = mock(ProductController.class);

    @MockBean
    private ProductService productService = mock(ProductService.class);

    @Test
    public void shouldReturnProductList() throws Exception {

        List<Product> listProducts = productRepository.findAll();
        int size = listProducts.size();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(
                listProducts,
                header,
                HttpStatus.OK
        );

        when(productRepository.findAll()).thenReturn(listProducts);
    }

    @Test
    public void shouldReturnProduct() throws Exception {
        Long id = 1L;
        Product product = productRepository.getById(id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(
                product,
                header,
                HttpStatus.OK
        );

        when(productRepository.getById(id)).thenReturn(product);
    }
}