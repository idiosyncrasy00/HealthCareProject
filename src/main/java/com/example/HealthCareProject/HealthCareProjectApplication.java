package com.example.HealthCareProject;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@EntityScan(basePackages ={"com.example.HealthCareProject"})
//@EnableAutoConfiguration
@Log4j2
public class HealthCareProjectApplication {
	//private static final Logger LOGGER = LogManager.getLogger(HealthCareProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HealthCareProjectApplication.class, args);

//		String logDir = System.getProperty("APP_LOG_ROOT", "/var/log");
//
//		LOGGER.info("Using log directory: " + logDir);
//		LOGGER.info("Info level log message");
//		LOGGER.debug("Debug level log message");
//		LOGGER.error("Error level log message");
	}

}
