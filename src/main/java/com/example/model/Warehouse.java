package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Warehouse")
@AllArgsConstructor
public class Warehouse {

    @Id
    @Column(name = "storage_rack")
    private int storageRack;

    @Column(name = "volume")
    private double volume;

    @Column(name = "max_weight")
    private int maxWeight;

    @OneToMany(mappedBy = "warehouse")
    private List<Article> articlesList;

    public Warehouse() {

    }
}
