package com.example.HealthCareProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan(basePackages ={"com.example.HealthCareProject"})
//@EnableAutoConfiguration
//@Log4j2

/**
 * SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 * - Configuration: Tags the class as a bean for application context
 * - EnableAutoConfiguration: Tell spring boot to start adding beans based on classpath settings,
 * other beans, and various property settings
 * - ComponentScan: Spring looking for other components, configurations, and services in the package,
 * letting it find the controllers
 */
public class HealthCareProjectApplication {
	//private static final Logger LOGGER = LogManager.getLogger(HealthCareProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HealthCareProjectApplication.class, args);
	}

}
