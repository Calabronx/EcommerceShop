package com.factorit.EcommerceShop.model;

import com.factorit.EcommerceShop.utils.CartEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_tbl")
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private CartEnum cartType;

    @Column(name = "cart_name")
    private String cartName;

    @Column(name = "cart_date")
    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean deleted;

    @Column(name = "buy_count")
    private int buyCount;

    @Column(name = "cart_descount")
    private int descount;

    @Column(name = "cart_totalprice")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<Product> productsList = new ArrayList<>();


    public ShoppingCart() {
    }

    public ShoppingCart(Long id, CartEnum cartType, String cartName, LocalDateTime createdAt, boolean deleted, int buyCount, int descount) {
        this.id = id;
        this.cartType = cartType;
        this.cartName = cartName;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.buyCount = buyCount;
        this.descount = descount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartEnum getCartType() {
        return cartType;
    }

    public void setCartType(CartEnum cartType) {
        this.cartType = cartType;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getDescount() {
        return descount;
    }

    public void setDescount(int descount) {
        this.descount = descount;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public void addProduct(Product product) {
        productsList.add(product);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", cartName='" + cartName + '\'' +
                ", createdAt=" + createdAt +
                ", buyCount=" + buyCount +
                ", descount=" + descount +
                ", totalAmount=" + totalAmount +
                ", productsList=" + productsList +
                '}';
    }
}
