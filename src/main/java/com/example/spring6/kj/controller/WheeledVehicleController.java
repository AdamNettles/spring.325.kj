package com.example.spring6.kj.controller;

import com.example.spring6.kj.model.WheeledVehicle;
import com.example.spring6.kj.persistence.MongoCrud;
import com.example.spring6.kj.service.PersistenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
public class WheeledVehicleController {

    MongoCrud mongoCrud;
    PersistenceService persistenceService;

    WheeledVehicleController(MongoCrud mongoCrud, PersistenceService persistenceService) {
        this.mongoCrud = mongoCrud;
        this.persistenceService = persistenceService;
    }

    @GetMapping(value = "/")
    ResponseEntity<String> root() {
        long size = mongoCrud.count();
        return ResponseEntity.ok(
        "Hello World, we have " +
                size + (size != 1 ? " vehicles" : " vehicle") +
                " in stock!"
        );
    }

    @PutMapping(value = "/api/wheeledVehicle")
    ResponseEntity<ApiResponse> addWheeledVehicle(@RequestBody WheeledVehicle wheeledVehicle) throws URISyntaxException {
        Optional<WheeledVehicle> o = mongoCrud.findById(wheeledVehicle.getId())
                .stream()
                .findAny();
        if(o.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(
                    "Already exists at /api/wheeledVehicle/" + o.get().getId(), wheeledVehicle
            ));
        } else {
            mongoCrud.save(wheeledVehicle);
            URI uri = new URI("/api/wheeledVehicle/" + (wheeledVehicle.getId()));
            return ResponseEntity.created(uri)
                    .body(new ApiResponse("WheeledVehicle added at " + uri, wheeledVehicle));
        }

    }

    @GetMapping(value = "/api/wheeledVehicle/{wheeledVehicleId}")
    ResponseEntity<WheeledVehicle> getVehicle(@PathVariable(name = "wheeledVehicleId") Integer wheeledVehicleId) {
        return mongoCrud.findById(wheeledVehicleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/api/wheeledVehicle/{wheeledVehicleId}")
    ResponseEntity<ApiResponse> deleteVehicle(@PathVariable(name = "wheeledVehicleId") Integer wheeledVehicleId) {
        return mongoCrud.findById(wheeledVehicleId)
                .map(it -> {
                    mongoCrud.deleteById(wheeledVehicleId);
                    return ResponseEntity.ok(new ApiResponse("Vehicle deleted", it));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/api/wheeledVehicle/all")
    ResponseEntity<ApiResponse> deleteVehicle() {
        long count = mongoCrud.count();
        if(count > 0) {
            mongoCrud.deleteAll();
            return ResponseEntity.ok(new ApiResponse("Deleted " + count + " entries", null));
        } else {
            return ResponseEntity.ok(new ApiResponse("Nothing to delete", null));
        }
    }

    @GetMapping("/api/wheeledVehicle")
    ResponseEntity<List<? extends WheeledVehicle>> getByQuery(@RequestParam("type") String type) throws ControllerException {
        return ResponseEntity.ok(persistenceService.findByType(type));
    }

}
