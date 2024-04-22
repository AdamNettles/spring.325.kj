package com.example.spring5.kj.model;

import java.util.Objects;

public class WheeledDrone extends WheeledVehicle {

    private final Boolean hasCamera;

    public WheeledDrone(Integer wheels, String model, Integer year, Boolean hasCamera) {
        super(wheels, model, year);
        this.hasCamera = hasCamera;
    }

    @SuppressWarnings("unused")
    public Boolean getHasCamera() {
        return hasCamera;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WheeledDrone w &&
            Objects.equals(this.getWheels(), w.getWheels()) &&
            Objects.equals(this.getModel(), w.getModel()) &&
            Objects.equals(this.getYear(), w.getYear()) &&
            Objects.equals(this.hasCamera, w.hasCamera);
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
