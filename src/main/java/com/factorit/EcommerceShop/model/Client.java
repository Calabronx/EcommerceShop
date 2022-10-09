package com.factorit.EcommerceShop.model;

import javax.persistence.*;

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

    public Client() {
    }

    public Client(String name, String level) {
        this.name = name;
        this.level = level;
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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
