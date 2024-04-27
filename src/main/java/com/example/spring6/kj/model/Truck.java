package com.example.spring6.kj.model;

import java.util.Objects;

public class Truck extends WheeledVehicle {

    private final Integer flatBedLength;

    public Truck(Integer wheels, String model, Integer year, Integer flatBedLength, Integer id) {
        super(wheels, model, year, id);
        this.flatBedLength = flatBedLength;
    }

    @SuppressWarnings("unused")
    public Integer getFlatBedLength() {
        return flatBedLength;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Truck t &&
                Objects.equals(this.getWheels(), t.getWheels()) &&
                Objects.equals(this.getModel(), t.getModel()) &&
                Objects.equals(this.getYear(), t.getYear()) &&
                Objects.equals(this.flatBedLength, t.flatBedLength);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(getWheels(), getModel(), getYear(), flatBedLength);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s",
                getWheels(), getModel(), getYear(), flatBedLength);
    }
}
