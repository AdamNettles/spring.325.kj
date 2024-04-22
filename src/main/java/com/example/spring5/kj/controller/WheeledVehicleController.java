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
    ApiResponse root() {
        return new ApiResponse(
        "Hello World, we have " + wheeledVehicles.size() +
                (wheeledVehicles.size() != 1 ? " vehicles" : " vehicle") +
                " in stock!",
        null);
    }

    @PutMapping(value = "/api/wheeledVehicle")
    ResponseEntity<ApiResponse> addCar(@RequestBody WheeledVehicle wheeledVehicle) throws URISyntaxException {
        int dex = wheeledVehicles.indexOf(wheeledVehicle);
        if(dex >= 0) {
            return ResponseEntity.ok(new ApiResponse(
                    "Already exists at /api/wheeledVehicle/" + dex, wheeledVehicle
            ));
        } else {
            wheeledVehicles.add(wheeledVehicle);
            URI uri = new URI("/api/wheeledVehicle/" + (wheeledVehicles.size() - 1));
            return ResponseEntity.created(uri)
                    .body(new ApiResponse("WheeledVehicle added at " + uri, wheeledVehicle));
        }
    }

    @GetMapping(value = "/api/wheeledVehicle/{wheeledVehicleId}")
    ResponseEntity<WheeledVehicle> getVehicle(@PathVariable(name = "wheeledVehicleId") Integer wheeledVehicleId) throws ControllerException {
        if(checkIdRange(wheeledVehicleId)) {
            return ResponseEntity.ok(wheeledVehicles.get(wheeledVehicleId));
        } else {
            throw new ControllerException("wheeledVehicleId " + wheeledVehicleId + " out of range");
        }
    }

    @DeleteMapping(value = "/api/wheeledVehicle/{wheeledVehicleId}")
    ResponseEntity<ApiResponse> deleteVehicle(@PathVariable(name = "wheeledVehicleId") Integer wheeledVehicleId) throws ControllerException {
        if(checkIdRange(wheeledVehicleId)) {
            WheeledVehicle toDelete = wheeledVehicles.get(wheeledVehicleId);
            wheeledVehicles.remove(wheeledVehicleId.intValue());
            return ResponseEntity.ok(new ApiResponse("Vehicle deleted", toDelete));
        } else {
            throw new ControllerException("wheeledVehicleId " + wheeledVehicleId + " out of range");
        }
    }

    private boolean checkIdRange(Integer wheeledVehicleId) {
        return wheeledVehicleId != null && wheeledVehicleId >= 0 && wheeledVehicleId < wheeledVehicles.size();
    }


}
