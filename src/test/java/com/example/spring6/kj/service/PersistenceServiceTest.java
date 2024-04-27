package com.example.spring6.kj.service;

import com.example.spring6.kj.controller.ControllerException;
import com.example.spring6.kj.model.Car;
import com.example.spring6.kj.model.Truck;
import com.example.spring6.kj.model.WheeledDrone;
import com.example.spring6.kj.model.WheeledVehicle;
import com.example.spring6.kj.persistence.WheeledVehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PersistenceServiceTest {

    private WheeledVehicleRepository mockRepoBean;
    private PersistenceService persistenceService;

    private final Car cherokee = new Car(4, "Cherokee", 1992, true, 2029046051);
    private final Truck silverado = new Truck(4, "Silverado", 1992, 10, -1914467106);
    private final WheeledDrone lightening = new WheeledDrone(4, "lightening", 1992, false, -2001240904);

    @BeforeEach
    void setup() {
        mockRepoBean = Mockito.mock(WheeledVehicleRepository.class);
        persistenceService = new PersistenceService(mockRepoBean);
    }

    @Test
    void testCar() throws ControllerException {
        doReturn(List.of(cherokee)).when(mockRepoBean).findByClass(Car.class.getCanonicalName());
        List<? extends WheeledVehicle> cars = persistenceService.findByType("car");
        verify(mockRepoBean, times(1)).findByClass(Car.class.getCanonicalName());
        assertThat(cars).isNotNull();
        assertThat(cars.size()).isEqualTo(1);
        assertThat(cars.get(0)).isInstanceOf(Car.class).isEqualTo(cherokee);
    }

    @Test
    void testTruck() throws ControllerException {
        doReturn(List.of(silverado)).when(mockRepoBean).findByClass(Truck.class.getCanonicalName());
        List<? extends WheeledVehicle> trucks = persistenceService.findByType("truck");
        verify(mockRepoBean, times(1)).findByClass(Truck.class.getCanonicalName());
        assertThat(trucks).isNotNull();
        assertThat(trucks.size()).isEqualTo(1);
        assertThat(trucks.get(0)).isInstanceOf(Truck.class).isEqualTo(silverado);
    }

    @Test
    void testWheeledDrone() throws ControllerException {
        doReturn(List.of(lightening)).when(mockRepoBean).findByClass(WheeledDrone.class.getCanonicalName());
        List<? extends WheeledVehicle> drones = persistenceService.findByType("wheeledDrone");
        verify(mockRepoBean, times(1)).findByClass(WheeledDrone.class.getCanonicalName());
        assertThat(drones).isNotNull();
        assertThat(drones.size()).isEqualTo(1);
        assertThat(drones.get(0)).isInstanceOf(WheeledDrone.class).isEqualTo(lightening);
    }

    @Test
    void testBadInput() {
        String badType = "badType";
        ControllerException cE = assertThrows(
                ControllerException.class,
                () -> persistenceService.findByType(badType)
        );
        assertThat(cE.getMessage()).isEqualTo("Unknown type specified: " + badType);
    }

}
