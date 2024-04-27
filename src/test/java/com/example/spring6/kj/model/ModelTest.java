package com.example.spring6.kj.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelTest {

    @Test
    void testCustomEqualsAndHash() {
        Car c = new Car(4, "fakeModel", 2000, true, 1);
        Car sameAsC = new Car(4, "fakeModel", 2000, true, 1);
        Car differentWheels = new Car(3, "fakeModel", 2000, true, 2);
        Car differentModel = new Car(4, "fakeModel2", 2000, true, 3);
        Car differentYear = new Car(4, "fakeModel", 2001, true, 4);
        Car differentHatch = new Car(4, "fakeModel", 2000, false, 5);

        assertThat(c).isEqualTo(sameAsC);
        assertThat(c).isNotEqualTo(differentWheels);
        assertThat(c).isNotEqualTo(differentModel);
        assertThat(c).isNotEqualTo(differentYear);
        assertThat(c).isNotEqualTo(differentHatch);

        assertThat(c.hashCode()).isEqualTo(sameAsC.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentWheels.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentModel.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentYear.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentHatch.hashCode());
    }

}
