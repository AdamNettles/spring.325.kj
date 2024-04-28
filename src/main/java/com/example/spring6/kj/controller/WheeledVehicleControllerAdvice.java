package com.example.spring6.kj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@ControllerAdvice(annotations = RestController.class)
public class WheeledVehicleControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(WheeledVehicleControllerAdvice.class);

    @ExceptionHandler({ControllerException.class})
    public final ResponseEntity<ApiError> handleException(Exception e) {
        log.error(e.getMessage());
        if(e instanceof ControllerException) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        } else {
            return ResponseEntity.internalServerError().body(
                    new ApiError("Unknown Exception with message " + e.getMessage())
            );
        }
    }

}
