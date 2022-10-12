package com.factorit.EcommerceShop.repository;

import com.factorit.EcommerceShop.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client getById(Long id);
    Client findByName(String name);
    List<Client> findAll();
    //Page<Client> findAllLevel(String level, Pageable pageable);
}
