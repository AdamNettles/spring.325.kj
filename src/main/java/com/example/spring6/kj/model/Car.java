package com.example.spring6.kj.model;

import java.util.Objects;

@SuppressWarnings("unused")
public class Car extends WheeledVehicle {

    private final Boolean isHatchBack;

    public Car(Integer wheels, String model, Integer year, Boolean isHatchBack, Integer id) {
        super(wheels, model, year, id);
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
        return  Objects.hash(getWheels(), getModel(), getYear(), isHatchBack);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", getWheels(), getModel(), getYear(), isHatchBack);
    }

}
