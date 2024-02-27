package com.vmoscalciuc.inventoryservice;

import com.vmoscalciuc.inventoryservice.model.*;
import com.vmoscalciuc.inventoryservice.repository.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.*;
import org.springframework.context.annotation.*;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setProductId("productNotebook1");
            inventory.setQuantity(100);

            Inventory inventory1 = new Inventory();
            inventory1.setProductId("65a926fe3f131d6c20d290d9");
            inventory1.setQuantity(110);

            inventoryRepository.save(inventory);
            inventoryRepository.save(inventory1);

        };
    }

}
