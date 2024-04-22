package com.example.spring5.kj.controller;

import com.example.spring5.kj.model.WheeledVehicle;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
record ApiResponse(
        String message,
        WheeledVehicle wheeledVehicle
){ }
