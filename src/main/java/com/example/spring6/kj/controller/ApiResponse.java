package com.example.spring6.kj.controller;

import com.example.spring6.kj.model.WheeledVehicle;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@JsonInclude(JsonInclude.Include.NON_NULL)
record ApiResponse(
        String message,
        WheeledVehicle wheeledVehicle
){ }
