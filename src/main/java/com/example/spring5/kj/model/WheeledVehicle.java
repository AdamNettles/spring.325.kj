package com.example.spring5.kj.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type" // field on which we differentiate objects
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "car"),
        @JsonSubTypes.Type(value = Truck.class, name = "truck"),
        @JsonSubTypes.Type(value = WheeledDrone.class, name = "wheeledDrone")
})
public abstract class WheeledVehicle {

    private final Integer wheels;
    private final String model;
    private final Integer year;

    protected WheeledVehicle(Integer wheels, String model, Integer year) {
        this.wheels = wheels;
        this.model = model;
        this.year = year;
    }

    public int getWheels() {
        return wheels;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }
}
