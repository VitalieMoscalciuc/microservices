package com.vmoscalciuc.deliveryservice.controller;

import com.vmoscalciuc.deliveryservice.service.DeliveryService;
import com.vmoscalciuc.deliveryservice.dto.DeliveryRequest;
import com.vmoscalciuc.deliveryservice.dto.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryResponse createDelivery(@RequestBody DeliveryRequest deliveryRequest){
        return deliveryService.createDelivery(deliveryRequest);
    }
}
