package com.example.spring6.kj.service;

import com.example.spring6.kj.controller.ControllerException;
import com.example.spring6.kj.model.Car;
import com.example.spring6.kj.model.Truck;
import com.example.spring6.kj.model.WheeledDrone;
import com.example.spring6.kj.model.WheeledVehicle;
import com.example.spring6.kj.persistence.WheeledVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceService {

    WheeledVehicleRepository wheeledVehicleRepository;

    public PersistenceService(WheeledVehicleRepository wheeledVehicleRepository) {
        this.wheeledVehicleRepository = wheeledVehicleRepository;
    }

    public List<? extends WheeledVehicle> findByType(String type) throws ControllerException {
        return switch (type) {
            case "car" -> wheeledVehicleRepository.findByClass(Car.class.getCanonicalName());
            case "truck" -> wheeledVehicleRepository.findByClass(Truck.class.getCanonicalName());
            case "wheeledDrone" -> wheeledVehicleRepository.findByClass(WheeledDrone.class.getCanonicalName());
            default -> throw new ControllerException("Unknown type specified: " + type);
        };
    }

}
