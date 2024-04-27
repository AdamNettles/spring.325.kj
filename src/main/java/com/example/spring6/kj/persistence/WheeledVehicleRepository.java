package com.example.spring6.kj.persistence;

import com.example.spring6.kj.model.WheeledVehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WheeledVehicleRepository extends MongoRepository<WheeledVehicle, Integer> {

    @Query("{_class: ?0}")
    List<? extends WheeledVehicle> findByClass(String classCanonicalName);

}
