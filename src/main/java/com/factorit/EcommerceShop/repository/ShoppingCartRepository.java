package com.factorit.EcommerceShop.repository;

import com.factorit.EcommerceShop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAll();
    Optional<ShoppingCart> findById(Long id);

    ShoppingCart getById(Long id);

}
