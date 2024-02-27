package com.vmoscalciuc.inventoryservice.controller;

import com.vmoscalciuc.inventoryservice.dto.*;
import com.vmoscalciuc.inventoryservice.model.Inventory;
import com.vmoscalciuc.inventoryservice.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestBody List<OrderLineItemsDto> productInfos){
            return inventoryService.isProductStock(productInfos);
    }

    @PostMapping("updateQuantity")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateQuantityOfProduct(@RequestBody OrderLineItemsDto productInfo){
        inventoryService.updateQuantity(productInfo);
    }
}
