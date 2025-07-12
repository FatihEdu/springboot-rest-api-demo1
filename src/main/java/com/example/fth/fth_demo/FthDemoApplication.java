package com.example.fth.fth_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FthDemoApplication.class, args);
	}

}
