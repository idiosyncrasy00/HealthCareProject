package com.example.HealthCareProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages ={"com.example.HealthCareProject"})
//@EnableAutoConfiguration
//@Log4j2

/**
 * SpringBootApplication =
 */
public class HealthCareProjectApplication {
	//private static final Logger LOGGER = LogManager.getLogger(HealthCareProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HealthCareProjectApplication.class, args);
	}

}
