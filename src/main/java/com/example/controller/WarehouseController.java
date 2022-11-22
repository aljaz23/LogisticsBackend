package com.example.controller;

import com.example.exception.ResourceException;
import com.example.model.Warehouse;
import com.example.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/logistics_warehouse")
    public String viewLogisticsWarehouse() {
        return "logisticsWarehouse";
    }

    @GetMapping("/logistics_list_warehouses")
    public String viewAllWarehouses(Model model) {
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        model.addAttribute("warehouseList", warehouseList);
        return "logisticsListWarehouses";
    }

    @GetMapping("/logistics_add_warehouse")
    public String viewAddWarehouseForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "logisticsAddWarehouse";
    }

    @PostMapping("logistics_add_warehouse")
    public String saveWarehouse(Warehouse warehouse, Model model) {
        List<Integer> listIds = warehouseService.getWarehousesIds();

        int inputStorageRack = warehouse.getStorageRack();

        if (listIds.contains(inputStorageRack)) {
            throw new ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Warehouse With given Storage Rack '%s' already exist", inputStorageRack));
        } else {
            warehouseService.saveWarehouse(warehouse);
            return "redirect:/logistics_add_warehouse";
        }
    }

}
