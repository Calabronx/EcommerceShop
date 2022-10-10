package com.factorit.EcommerceShop.repository;

import com.factorit.EcommerceShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> getByName(String name);
    Product getById(Long id);
    Product findByName(String name);
}
