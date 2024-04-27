package com.example.spring6.kj.controller;

import com.example.spring6.kj.model.Car;
import com.example.spring6.kj.model.Truck;
import com.example.spring6.kj.model.WheeledDrone;
import com.example.spring6.kj.model.WheeledVehicle;
import com.example.spring6.kj.persistence.MongoCrud;
import com.example.spring6.kj.service.PersistenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WheeledVehicleControllerTest {

	@Autowired
	WheeledVehicleController wheeledVehicleController;

	@Autowired
	TestRestTemplate restTemplate;

	@MockBean
	MongoCrud mockMongoCrud;

	@MockBean
	PersistenceService mockPersistenceService;

	@SuppressWarnings("unused")
	@LocalServerPort
	private int port;

	private String baseUrl;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Car car1 = new Car(4, "model", 2000, true, 1228059877);
	private final Car car2 = new Car(4, "model", 2001, true, 1228059908);
	private final Car cherokee = new Car(4, "Cherokee", 1992, true, 2029046051);
	private final Truck silverado = new Truck(4, "Silverado", 1992, 10, -1914467106);
	private final WheeledDrone lightening = new WheeledDrone(4, "lightening", 1992, false, -2001240904);

	@BeforeEach
    void setup() {
		this.baseUrl = "http://localhost:"+this.port;
	}

	@AfterEach
	void reset() {
	}

	@Test
	void contextLoads() {
		assertNotNull(wheeledVehicleController);
		assertNotNull(restTemplate);
		assertThat(port).isGreaterThan(0);
	}

	@Test
	void helloWorld() {
		given(mockMongoCrud.count()).willReturn(0L);
		assertThat(helloHelper()).isEqualTo("Hello World, we have 0 vehicles in stock!");
		verify(mockMongoCrud, times(1)).count();

		given(mockMongoCrud.findById(car1.getId())).willReturn(Optional.empty());
		given(mockMongoCrud.save(car1)).willReturn(car1);
		putVehicle(car1);
		verify(mockMongoCrud, times(1)).findById(car1.getId());
		verify(mockMongoCrud, times(1)).save(car1);

		given(mockMongoCrud.count()).willReturn(1L);
		assertThat(helloHelper()).isEqualTo("Hello World, we have 1 vehicle in stock!");
		verify(mockMongoCrud, times(2)).count();

		given(mockMongoCrud.findById(car1.getId())).willReturn(Optional.of(car1));
		putVehicle(car1);
		verify(mockMongoCrud, times(2)).findById(car1.getId());

		given(mockMongoCrud.count()).willReturn(1L);
		assertThat(helloHelper()).isEqualTo("Hello World, we have 1 vehicle in stock!");
		verify(mockMongoCrud, times(3)).count();

		given(mockMongoCrud.findById(car2.getId())).willReturn(Optional.empty());
		given(mockMongoCrud.save(car2)).willReturn(car2);
		putVehicle(car2);
		verify(mockMongoCrud, times(1)).findById(car2.getId());
		verify(mockMongoCrud, times(1)).save(car2);

		given(mockMongoCrud.count()).willReturn(2L);
		assertThat(helloHelper()).isEqualTo("Hello World, we have 2 vehicles in stock!");
		verify(mockMongoCrud, times(4)).count();
	}

	@Test
	void testPutCar() throws URISyntaxException, JsonProcessingException {
		given(mockMongoCrud.findById(cherokee.getId())).willReturn(Optional.empty());
		given(mockMongoCrud.save(cherokee)).willReturn(cherokee);
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				objectMapper.readTree(objectMapper.writeValueAsString(cherokee))
		);
		verify(mockMongoCrud, times(1)).findById(cherokee.getId());
		verify(mockMongoCrud, times(1)).save(cherokee);

		given(mockMongoCrud.findById(cherokee.getId())).willReturn(Optional.of(cherokee));
		Car actual = getHelper(Car.class, cherokee.getId());
		verify(mockMongoCrud, times(2)).findById(cherokee.getId());

		assertThat(actual).isEqualTo(cherokee);
	}

	@Test
	void testPutTruck() throws URISyntaxException, JsonProcessingException {
		given(mockMongoCrud.findById(silverado.getId())).willReturn(Optional.empty());
		given(mockMongoCrud.save(silverado)).willReturn(silverado);
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				objectMapper.readTree(objectMapper.writeValueAsString(silverado))
		);
		verify(mockMongoCrud, times(1)).findById(silverado.getId());
		verify(mockMongoCrud, times(1)).save(silverado);

		given(mockMongoCrud.findById(silverado.getId())).willReturn(Optional.of(silverado));
		Truck actual = getHelper(Truck.class, silverado.getId());
		verify(mockMongoCrud, times(2)).findById(silverado.getId());

		assertThat(actual).isEqualTo(silverado);
	}

	@Test
	void testPutWheeledDrone() throws URISyntaxException, JsonProcessingException {
		given(mockMongoCrud.findById(lightening.getId())).willReturn(Optional.empty());
		given(mockMongoCrud.save(lightening)).willReturn(lightening);
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				objectMapper.readTree(objectMapper.writeValueAsString(lightening))
		);
		verify(mockMongoCrud, times(1)).findById(lightening.getId());
		verify(mockMongoCrud, times(1)).save(lightening);

		given(mockMongoCrud.findById(lightening.getId())).willReturn(Optional.of(lightening));
		WheeledDrone actual = getHelper(WheeledDrone.class, lightening.getId());
		verify(mockMongoCrud, times(2)).findById(lightening.getId());

		assertThat(actual).isEqualTo(lightening);
	}

	@Test
	void testDeleteDex() {
		URI url = URI.create("/api/wheeledVehicle/" + car1.getId());

		given(mockMongoCrud.findById(car1.getId())).willReturn(Optional.empty());
		given(mockMongoCrud.save(car1)).willReturn(car1);
		putVehicle(car1);
		verify(mockMongoCrud, times(1)).findById(car1.getId());
		verify(mockMongoCrud, times(1)).save(car1);

		given(mockMongoCrud.findById(car1.getId())).willReturn(Optional.of(car1));
		Car actual = getHelper(Car.class, car1.getId());
		verify(mockMongoCrud, times(2)).findById(car1.getId());
		assertThat(actual).isEqualTo(car1);

		given(mockMongoCrud.findById(car1.getId())).willReturn(Optional.of(car1));
		ApiResponse deleteResponse = restTemplate.exchange(
				url,
				HttpMethod.DELETE,
				null,
				ApiResponse.class
		).getBody();
		verify(mockMongoCrud, times(3)).findById(car1.getId());
		verify(mockMongoCrud, times(1)).deleteById(car1.getId());
		assertThat(deleteResponse).isEqualTo(new ApiResponse("Vehicle deleted", car1));

		given(mockMongoCrud.findById(car1.getId())).willReturn(Optional.empty());
		ResponseEntity getResponse = restTemplate.getForEntity(
				baseUrl + "/api/wheeledVehicle/" + car1.getId(), ResponseEntity.class
		);
		verify(mockMongoCrud, times(4)).findById(car1.getId());
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
	}

	@Test
	void testDeleteAll() {
		URI uri = URI.create(baseUrl + "/api/wheeledVehicle/all");
		when(mockMongoCrud.count()).thenReturn(1L);
		ApiResponse deleteResponse = restTemplate.exchange(
				uri,
				HttpMethod.DELETE,
				null,
				ApiResponse.class
		).getBody();
		verify(mockMongoCrud, times(1)).count();
		verify(mockMongoCrud, times(1)).deleteAll();
		assertNotNull(deleteResponse);
		assertThat(deleteResponse.message()).isEqualTo("Deleted 1 entries");

		when(mockMongoCrud.count()).thenReturn(0L);
		ApiResponse deleteResponse2 = restTemplate.exchange(
				uri,
				HttpMethod.DELETE,
				null,
				ApiResponse.class
		).getBody();
		verify(mockMongoCrud, times(2)).count();
		assertNotNull(deleteResponse2);
		assertThat(deleteResponse2.message()).isEqualTo("Nothing to delete");
	}

	@Test
	void testFindByType() throws Exception {
		String theType = "irrelevant";
		URI uri = URI.create(baseUrl + "/api/wheeledVehicle?type=" + theType);
		doReturn(List.of(car1)).when(mockPersistenceService).findByType(theType);
		ResponseEntity<List<? extends WheeledVehicle>> response = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {}
		);
		verify(mockPersistenceService, times(1)).findByType(theType);
		assert response != null;
		assertThat(response.getBody()).isNotNull().isInstanceOf(ArrayList.class);
		assertThat(response.getBody().get(0)).isInstanceOf(Car.class).isEqualTo(car1);
	}

	private void putVehicle(WheeledVehicle wheeledVehicle) {
		restTemplate.put(
				URI.create(baseUrl + "/api/wheeledVehicle"),
				wheeledVehicle
		);
	}

	private String helloHelper() {
		return restTemplate.getForEntity(
				URI.create(baseUrl), String.class
		).getBody();
	}

	private <T extends WheeledVehicle> T getHelper(Class<T> tClass, Integer dex) {
		return restTemplate.getForEntity(
				URI.create(baseUrl + "/api/wheeledVehicle/" + dex), tClass
		).getBody();
	}


}
