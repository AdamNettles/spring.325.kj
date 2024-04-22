package com.example.spring5.kj.controller;

import com.example.spring5.kj.model.WheeledVehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SuppressWarnings("unused")
@RestController
public class WheeledVehicleController {

    private final List<WheeledVehicle> wheeledVehicles;

    WheeledVehicleController(List<WheeledVehicle> wheeledVehicles) {
        this.wheeledVehicles = wheeledVehicles;
    }

    @GetMapping(value = "/")
    String root() {
        return "Hello World, we have " + wheeledVehicles.size() +
                 (wheeledVehicles.size() != 1 ? " vehicles" : " vehicle") +
                " in stock!";
    }

    @PutMapping(value = "/api/wheeledVehicle")
    ResponseEntity<String> addCar(@RequestBody WheeledVehicle wheeledVehicle) throws URISyntaxException {
        int dex = wheeledVehicles.indexOf(wheeledVehicle);
        if(dex >= 0) {
            return ResponseEntity.ok("Already exists at /api/wheeledVehicle/" + dex);
        } else {
            wheeledVehicles.add(wheeledVehicle);
            URI uri = new URI("/api/wheeledVehicle/" + (wheeledVehicles.size() - 1));
            return ResponseEntity
                    .created(uri)
                    .body("WheeledVehicle added at " + uri);
        }
    }

    @GetMapping(value = "/api/wheeledVehicle/{wheeledVehicleId}")
    ResponseEntity<WheeledVehicle> getVehicle(@PathVariable(name = "wheeledVehicleId") Integer wheeledVehicleId) throws ControllerException {
        if(wheeledVehicleId < 0 || wheeledVehicleId >= wheeledVehicles.size()) {
            throw new ControllerException("wheeledVehicleId " + wheeledVehicleId + " out of range");
        } else {
            return ResponseEntity.ok(wheeledVehicles.get(wheeledVehicleId));
        }
    }

}
