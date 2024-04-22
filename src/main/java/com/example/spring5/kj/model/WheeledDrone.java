package com.example.spring5.kj.model;

import java.util.Objects;

public class WheeledDrone extends WheeledVehicle {

    private final Boolean hasCamera;

    public WheeledDrone(Integer wheels, String model, Integer year, Boolean hasCamera) {
        super(wheels, model, year);
        this.hasCamera = hasCamera;
    }

    public Boolean getHasCamera() {
        return hasCamera;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WheeledDrone c &&
            Objects.equals(this.getWheels(), c.getWheels()) &&
            Objects.equals(this.getModel(), c.getModel()) &&
            Objects.equals(this.getYear(), c.getYear()) &&
            Objects.equals(this.hasCamera, c.hasCamera);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(
                this.getWheels(),
                this.getModel(),
                this.getYear(),
                this.hasCamera
        );
    }

}
