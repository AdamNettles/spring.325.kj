package com.example.spring6.kj.persistence;

import com.example.spring6.kj.model.WheeledVehicle;
import org.springframework.data.repository.CrudRepository;

public interface MongoCrud extends CrudRepository<WheeledVehicle, Integer> { }
