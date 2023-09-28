package com.vmoscalciuc.productservice.repository;

import com.vmoscalciuc.productservice.model.*;
import org.springframework.data.mongodb.repository.*;

public interface ProductRepository extends MongoRepository<Product,String> {
}
