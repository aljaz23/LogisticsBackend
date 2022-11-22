package com.example.repository;

import com.example.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {

    @Query(value = "SELECT storage_rack FROM warehouse", nativeQuery = true)
    List<Integer>getAllIds();

}
