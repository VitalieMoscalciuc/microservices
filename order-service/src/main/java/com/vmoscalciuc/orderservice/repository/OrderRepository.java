package com.vmoscalciuc.orderservice.repository;

import com.vmoscalciuc.orderservice.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
