package com.example.spring5.kj;

import com.example.spring5.kj.model.Car;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BasicTests {

    @Test
    void basicDataTest() {
        Car c = new Car(4, "fakeModel", 2000, true);
        Car sameAsC = new Car(4, "fakeModel", 2000, true);
        Car differentWheels = new Car(3, "fakeModel", 2000, true);
        Car differentModel = new Car(4, "fakeModel2", 2000, true);
        Car differentYear = new Car(4, "fakeModel", 2001, true);

        assertThat(c).isEqualTo(sameAsC);
        assertThat(c).isNotEqualTo(differentWheels);
        assertThat(c).isNotEqualTo(differentModel);
        assertThat(c).isNotEqualTo(differentYear);

        assertThat(c.hashCode()).isEqualTo(sameAsC.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentWheels.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentModel.hashCode());
        assertThat(c.hashCode()).isNotEqualTo(differentYear.hashCode());
    }

}
