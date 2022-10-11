package com.factorit.EcommerceShop.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_tbl")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_name")
    private String name;

    @Column(name = "client_level")
    private String level;

    @Column(name = "next_monthbonus")
    private String nextMonthBonus;

    @Column(name = "is_vip")
    private boolean isVipClient = false;
    @Column(name = "vip_obtained")
    private LocalDateTime vipAdquireDate;

    @Column(name = "end_vip")
    private LocalDateTime endMembershipDate;

    @Column(name = "buys_amount")
    private BigDecimal client_buys;

    public Client() {
    }

    public Client(Long id, String name, String level, String nextMonthBonus, boolean isVipClient, LocalDateTime vipAdquireDate, LocalDateTime endMembershipDate, BigDecimal client_buys) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.nextMonthBonus = nextMonthBonus;
        this.isVipClient = isVipClient;
        this.vipAdquireDate = vipAdquireDate;
        this.endMembershipDate = endMembershipDate;
        this.client_buys = client_buys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNextMonthBonus() {
        return nextMonthBonus;
    }

    public void setNextMonthBonus(String nextMonthBonus) {
        this.nextMonthBonus = nextMonthBonus;
    }

    public boolean isVipClient() {
        return isVipClient;
    }

    public void setVipClient(boolean vipClient) {
        isVipClient = vipClient;
    }

    public LocalDateTime getVipAdquireDate() {
        return vipAdquireDate;
    }

    public void setVipAdquireDate(LocalDateTime vipAdquireDate) {
        this.vipAdquireDate = vipAdquireDate;
    }

    public LocalDateTime getEndMembershipDate() {
        return endMembershipDate;
    }

    public void setEndMembershipDate(LocalDateTime endMembershipDate) {
        this.endMembershipDate = endMembershipDate;
    }

    public BigDecimal getClient_buys() {
        return client_buys;
    }

    public void setClient_buys(BigDecimal client_buys) {
        this.client_buys = client_buys;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", nextMonthBonus='" + nextMonthBonus + '\'' +
                ", isVipClient=" + isVipClient +
                ", vipAdquireDate=" + vipAdquireDate +
                ", endMembershipDate=" + endMembershipDate +
                ", client_buys=" + client_buys +
                '}';
    }
}
