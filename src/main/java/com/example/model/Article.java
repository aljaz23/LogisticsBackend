package com.example.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "article_name", nullable = false)
    private String articleName;

    @Column(name = "supplier", nullable = false)
    private String supplier;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "warehouse_storage_rack")
    private Warehouse warehouse;

    public Article() {

    }

    public int getStorageRack() {
        return warehouse.getStorageRack();
    }
}


