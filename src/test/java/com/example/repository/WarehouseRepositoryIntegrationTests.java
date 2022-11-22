package com.example.repository;

import com.example.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WarehouseRepositoryIntegrationTests {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    TestEntityManager testEntityManager;


    @Test
    void Should_Save_Warehouse(){
        Warehouse warehouse = warehouseSetup();
        warehouseRepository.save(warehouse);

        Warehouse existingWarehouse = testEntityManager.find(Warehouse.class,warehouse.getStorageRack());


        assertThat(existingWarehouse).isNotNull();

    }
    @Test
    void Should_Get_All_Warehouse_Ids(){
        List<Integer> warehouseIds = warehouseRepository.getAllIds();
        assertThat(warehouseIds.size()).isGreaterThanOrEqualTo(1);
    }
    @Test
    void Should_Delete_Warehouse(){
        Warehouse warehouse = warehouseSetup();
        warehouseRepository.save(warehouse);

        warehouseRepository.deleteById(warehouse.getStorageRack());

        Warehouse existingWarehouse = testEntityManager.find(Warehouse.class,warehouse.getStorageRack());
        assertThat(existingWarehouse).isNull();
    }

    Warehouse warehouseSetup(){
        Warehouse warehouse = new Warehouse();
        warehouse.setStorageRack(1);
        warehouse.setMaxWeight(5000);
        warehouse.setVolume(4500);
        return warehouse;

    }

}
