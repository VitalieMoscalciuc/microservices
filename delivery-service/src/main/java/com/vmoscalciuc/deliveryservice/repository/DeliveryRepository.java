package com.vmoscalciuc.deliveryservice.repository;

import com.vmoscalciuc.deliveryservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
