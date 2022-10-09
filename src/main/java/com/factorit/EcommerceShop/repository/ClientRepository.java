package com.factorit.EcommerceShop.repository;

import com.factorit.EcommerceShop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client getById(Long id);
    Client findByName(String name);
}
