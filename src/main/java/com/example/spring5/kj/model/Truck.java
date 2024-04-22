package com.example.spring5.kj.model;

import java.util.Objects;

public class Truck extends WheeledVehicle {

    private final Integer flatBedLength;

    protected Truck(Integer wheels, String model, Integer year, Integer flatBedLength) {
        super(wheels, model, year);
        this.flatBedLength = flatBedLength;
    }

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
        return  Objects.hash(this.getWheels(), this.getModel(), this.getYear(), this.flatBedLength);
    }
}
