package com.vmoscalciuc.inventoryservice.repository;

import com.vmoscalciuc.inventoryservice.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findByProductIdIn(List<String> productId);

    Optional<Inventory> findByProductId(String productId);

    @Modifying
    @Query("update Inventory i set i.quantity=i.quantity-:quantity where i.productId=:productId")
    void updateQuantityOfProducts(String productId, Integer quantity);
}
