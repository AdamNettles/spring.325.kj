package com.example.spring5.kj;

import com.example.spring5.kj.controller.WheeledVehicleController;
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
	}

	@Test
	void helloWorld() throws URISyntaxException {
		assertThat(helloHelper()).isEqualTo("Hello World, we have 0 vehicles in stock!");
		putCar(new Car(4, "model", 2000, true));
		assertThat(helloHelper()).isEqualTo("Hello World, we have 1 vehicle in stock!");
		putCar(new Car(4, "model", 2000, true));
		assertThat(helloHelper()).isEqualTo("Hello World, we have 1 vehicle in stock!");
		putCar(new Car(4, "model", 2001, true));
		assertThat(helloHelper()).isEqualTo("Hello World, we have 2 vehicles in stock!");
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
		Car actual = restTemplate.getForEntity(
				new URI(baseUrl + "/api/wheeledVehicle/0"),
				Car.class
		).getBody();
		assertThat(actual).isEqualTo(
				new Car(4, "Cherokee", 1992, true)
		);
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
		Truck actual = restTemplate.getForEntity(
				new URI(baseUrl + "/api/wheeledVehicle/0"),
				Truck.class
		).getBody();
		assertThat(actual).isEqualTo(
				new Truck(4, "Silverado", 1992, 10)
		);
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
		WheeledDrone actual = restTemplate.getForEntity(
				new URI(baseUrl + "/api/wheeledVehicle/0"),
				WheeledDrone.class
		).getBody();
		assertThat(actual).isEqualTo(
				new WheeledDrone(4, "lightening", 1992, false)
		);
	}

	private void putCar(Car car) throws URISyntaxException {
		restTemplate.put(
				new URI(baseUrl + "/api/wheeledVehicle"),
				car
		);
	}

	private String helloHelper() throws URISyntaxException {
		return restTemplate.getForEntity(
				new URI(baseUrl),String.class
		).getBody();
	}


}
