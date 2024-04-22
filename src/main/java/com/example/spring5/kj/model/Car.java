package com.example.spring5.kj.model;

import java.util.Objects;

public class Car extends WheeledVehicle {

    private final Boolean isHatchBack;

    public Car(Integer wheels, String model, Integer year, Boolean isHatchBack) {
        super(wheels, model, year);
        this.isHatchBack = isHatchBack;
    }

    public Boolean getIsHatchBack() {
        return isHatchBack;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Car c &&
            Objects.equals(this.getWheels(), c.getWheels()) &&
            Objects.equals(this.getModel(), c.getModel()) &&
            Objects.equals(this.getYear(), c.getYear()) &&
            Objects.equals(this.isHatchBack, c.isHatchBack);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(this.getWheels(), this.getModel(), this.getYear(), this.isHatchBack);
    }

}
