package com.example.spring5.kj;

import com.example.spring5.kj.controller.WheeledVehicleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.net.URI;
import java.net.URISyntaxException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@Autowired
	WheeledVehicleController wheeledVehicleController;

	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
		assertNotNull(wheeledVehicleController);
	}

	@Test
	void helloWorld() throws URISyntaxException {
		String s = restTemplate.getForEntity(new URI("http://localhost:"+port+"/"),String.class).getBody();
		assertThat(s).isEqualTo("Hello World");
	}

}
