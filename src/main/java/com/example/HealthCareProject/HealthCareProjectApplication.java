package com.example.HealthCareProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages ={"com.example.HealthCareProject"})
public class HealthCareProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareProjectApplication.class, args);
	}

}
