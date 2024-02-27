package com.vmoscalciuc.deliveryservice.service;

import com.vmoscalciuc.deliveryservice.dto.DeliveryRequest;
import com.vmoscalciuc.deliveryservice.dto.DeliveryResponse;
import com.vmoscalciuc.deliveryservice.model.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vmoscalciuc.deliveryservice.repository.DeliveryRepository;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest){
        deliveryRepository.save(mapDtoToEntity(deliveryRequest));
        return new DeliveryResponse();
    }

    private Delivery mapDtoToEntity(DeliveryRequest deliveryRequest){
        return null;
    }
}
