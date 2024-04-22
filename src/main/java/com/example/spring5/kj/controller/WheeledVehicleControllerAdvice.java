package com.example.spring5.kj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class WheeledVehicleControllerAdvice {

    @ExceptionHandler({ControllerException.class})
    public final ResponseEntity<ApiError> handleException(Exception e) {
        if(e instanceof ControllerException) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        } else {
            return ResponseEntity.internalServerError().body(
                    new ApiError("Unknown Exception with message " + e.getMessage())
            );
        }
    }

}
