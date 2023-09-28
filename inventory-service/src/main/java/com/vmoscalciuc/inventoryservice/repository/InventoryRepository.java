package com.vmoscalciuc.inventoryservice.repository;

import com.vmoscalciuc.inventoryservice.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findBySkuCodeIn(List<String> skuCode);
}
