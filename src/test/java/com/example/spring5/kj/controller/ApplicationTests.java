package com.example.spring5.kj.controller;

import com.example.spring5.kj.model.Car;
import com.example.spring5.kj.model.Truck;
import com.example.spring5.kj.model.WheeledDrone;
import com.example.spring5.kj.model.WheeledVehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@Autowired
	WheeledVehicleController wheeledVehicleController;

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	List<WheeledVehicle> wheeledVehicles;

	@SuppressWarnings("unused")
	@LocalServerPort
	private int port;

	private String baseUrl;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Car car1 = new Car(4, "model", 2000, true);
	private final Car car2 = new Car(4, "model", 2001, true);
	private final Car cherokee = new Car(4, "Cherokee", 1992, true);
	private final Truck silverado = new Truck(4, "Silverado", 1992, 10);
	private final WheeledDrone lightening = new WheeledDrone(4, "lightening", 1992, false);

	@BeforeEach
    void setup() {
		this.baseUrl = "http://localhost:"+this.port;
	}

	@AfterEach
	void reset() {
		this.wheeledVehicles.clear();
	}

	@Test
	void contextLoads() {
		assertNotNull(wheeledVehicleController);
		assertNotNull(wheeledVehicles);
		assertNotNull(restTemplate);
		assertThat(port).isGreaterThan(0);
	}

	@Test
	void helloWorld() throws URISyntaxException {
		assertThat(helloHelper()).isEqualTo(new ApiResponse("Hello World, we have 0 vehicles in stock!", null));
		putVehicle(car1);
		assertThat(helloHelper()).isEqualTo(new ApiResponse("Hello World, we have 1 vehicle in stock!", null));
		putVehicle(car1);
		assertThat(helloHelper()).isEqualTo(new ApiResponse("Hello World, we have 1 vehicle in stock!", null));
		putVehicle(car2);
		assertThat(helloHelper()).isEqualTo(new ApiResponse("Hello World, we have 2 vehicles in stock!", null));
	}

	@Test
	void testParseCar() throws URISyntaxException, JsonProcessingException {
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				objectMapper.readTree(
						"""
								{
									"type": "car",
									"wheels": 4,
									"model": "Cherokee",
									"year": 1992,
									"isHatchBack": true
								}"""
				)
		);
		Car actual = getHelper(Car.class, 0);
		assertThat(actual).isEqualTo(cherokee);
	}

	@Test
	void testParseTruck() throws URISyntaxException, JsonProcessingException {
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				objectMapper.readTree(
						"""
								{
									"type": "truck",
									"wheels": 4,
									"model": "Silverado",
									"year": 1992,
									"flatBedLength": 10
								}"""
				)
		);
		Truck actual = getHelper(Truck.class, 0);
		assertThat(actual).isEqualTo(silverado);
	}

	@Test
	void testParseWheeledDrone() throws URISyntaxException, JsonProcessingException {
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				objectMapper.readTree(
						"""
								{
									"type": "wheeledDrone",
									"wheels": 4,
									"model": "lightening",
									"year": 1992,
									"hasCamera": false
								}"""
				)
		);
		WheeledDrone actual = getHelper(WheeledDrone.class, 0);
		assertThat(actual).isEqualTo(lightening);
	}

	@Test
	void testDeleteDex() throws URISyntaxException {
		URI url = new URI(baseUrl + "/api/wheeledVehicle/" + 0);
		putVehicle(car1);
		Car actual = getHelper(Car.class, 0);
		assertThat(actual).isEqualTo(car1);
		assertThat(wheeledVehicles.size()).isEqualTo(1);
		ApiResponse deleteResponse = restTemplate.exchange(
				url,
				HttpMethod.DELETE,
				null,
				ApiResponse.class
		).getBody();
		assertThat(deleteResponse).isEqualTo(new ApiResponse("Vehicle deleted", car1));
		assertThat(wheeledVehicles.size()).isEqualTo(0);
		ApiResponse getResponse = restTemplate.getForEntity(
				baseUrl + "/api/wheeledVehicle/" + 0, ApiResponse.class
		).getBody();
		assertThat(getResponse).isEqualTo(new ApiResponse("wheeledVehicleId 0 out of range", null));
	}

	private void putVehicle(WheeledVehicle wheeledVehicle) throws URISyntaxException {
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				wheeledVehicle
		);
	}

	private ApiResponse helloHelper() throws URISyntaxException {
		return restTemplate.getForEntity(
				new URI(baseUrl), ApiResponse.class
		).getBody();
	}

	private <T extends WheeledVehicle> T getHelper(Class<T> tClass, Integer dex) throws URISyntaxException {
		return restTemplate.getForEntity(
				new URI(baseUrl + "/api/wheeledVehicle/" + dex), tClass
		).getBody();
	}


}
