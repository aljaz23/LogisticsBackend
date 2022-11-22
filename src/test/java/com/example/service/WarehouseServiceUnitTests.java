package com.example.service;

import com.example.model.Warehouse;
import com.example.repository.UserRepository;
import com.example.repository.WarehouseRepository;
import com.example.service.impl.UserServiceImpl;
import com.example.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceUnitTests {

    @Mock
    WarehouseRepository warehouseRepository;

    @InjectMocks
    WarehouseServiceImpl warehouseServiceImpl;

    @Test
    void Should_Save_Warehouse() {
        Warehouse warehouse = setupWarehouse1();
        warehouseServiceImpl.saveWarehouse(warehouse);

        when(warehouseRepository.findById(warehouse.getStorageRack())).thenReturn(Optional.of(warehouse));
        Warehouse foundWarehouse = warehouseRepository.findById(warehouse.getStorageRack()).get();

        assertThat(foundWarehouse).isNotNull();

    }

    @Test
    void Should_Get_All_Warehouses() {
        Warehouse warehouse1 = setupWarehouse1();
        Warehouse warehouse2 = setupWarehouse2();
        List<Warehouse> expectedWarehouseList = List.of(warehouse1, warehouse2);
        warehouseRepository.saveAll(expectedWarehouseList);

        when(warehouseRepository.findAll()).thenReturn(Optional.of(expectedWarehouseList).get());
        List<Warehouse> actualWarehouseList = warehouseRepository.findAll();

        assertThat(expectedWarehouseList).isEqualTo(actualWarehouseList);
    }

    Warehouse setupWarehouse1() {
        Warehouse warehouse = new Warehouse();
        warehouse.setStorageRack(1);
        warehouse.setMaxWeight(1000);
        warehouse.setVolume(10000);
        return warehouse;
    }

    Warehouse setupWarehouse2() {
        Warehouse warehouse = new Warehouse();
        warehouse.setStorageRack(2);
        warehouse.setMaxWeight(500);
        warehouse.setVolume(500);
        return warehouse;

    }

}

