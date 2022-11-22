package com.example.service;

import com.example.model.Article;
import com.example.model.Warehouse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseService {
    Warehouse saveWarehouse(Warehouse warehouse);

    List<Warehouse> getAllWarehouses();



    List<Integer> getWarehousesIds();

}
