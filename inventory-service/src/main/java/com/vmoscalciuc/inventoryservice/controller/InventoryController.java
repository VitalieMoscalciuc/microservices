package com.vmoscalciuc.inventoryservice.controller;

import com.vmoscalciuc.inventoryservice.dto.*;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
            return inventoryService.IsInStock(skuCode);
    }
}
