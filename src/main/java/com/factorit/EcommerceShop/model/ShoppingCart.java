package com.factorit.EcommerceShop.model;

import com.factorit.EcommerceShop.utils.CartEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_tbl")
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "ShoppingCart", propOrder = {
//        "cartName",
//        "totalAmount",
//        "productsList"
//})
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CartEnum cartType;

    //@XmlElement(required = true)
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

    //@XmlElement(required = true)
    @Column(name = "cart_totalprice")
    private BigDecimal totalAmount;

    @Column(name = "inicial_price")
    private BigDecimal inicialPrice;

    @Column(name = "is_bought")
    private boolean hasBought;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "is_inactive")
    private boolean deleteInactive;

    //@XmlElement(required = true)

    //Asociacion de uno a muchos con OneToMany
    //Un carrito tiene muchos productos
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    //@JsonBackReference
    @JsonManagedReference
    private List<Product> productsList = new ArrayList<>();


    public ShoppingCart() {
    }

    public ShoppingCart(Long id, CartEnum cartType, String cartName, LocalDateTime createdAt, boolean deleted, int buyCount, int descount, BigDecimal totalAmount, BigDecimal inicialPrice, boolean hasBought, String clientName, boolean deleteInactive, List<Product> productsList) {
        this.id = id;
        this.cartType = cartType;
        this.cartName = cartName;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.buyCount = buyCount;
        this.descount = descount;
        this.totalAmount = totalAmount;
        this.inicialPrice = inicialPrice;
        this.hasBought = hasBought;
        this.clientName = clientName;
        this.deleteInactive = deleteInactive;
        this.productsList = productsList;
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

    public boolean isHasBought() {
        return hasBought;
    }

    public void setHasBought(boolean hasBought) {
        this.hasBought = hasBought;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isDeleteInactive() {
        return deleteInactive;
    }

    public void setDeleteInactive(boolean deleteInactive) {
        this.deleteInactive = deleteInactive;
    }


    public BigDecimal getInicialPrice() {
        return inicialPrice;
    }

    public void setInicialPrice(BigDecimal inicialPrice) {
        this.inicialPrice = inicialPrice;
    }


    @Override
    public String toString() {
        return
                "id=" + id +
                ", cartName='" + cartName + '\'' +
                ", createdAt=" + createdAt +
                ", buyCount=" + buyCount +
                ", descount=" + descount +
                ", totalAmount=" + totalAmount +
                ", inicialPrice=" + inicialPrice +
                ", hasBought=" + hasBought +
                ", clientName='" + clientName + '\'' +
                ", deleteInactive=" + deleteInactive +
                ", productsList=" + productsList +
                '}';
    }
}
