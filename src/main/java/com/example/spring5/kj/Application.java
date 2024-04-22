package com.example.spring5.kj;

import com.example.spring5.kj.model.WheeledVehicle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean("wheeledVehicles")
	List<WheeledVehicle> wheeledVehicles() {
		return new ArrayList<>();
	}

}
