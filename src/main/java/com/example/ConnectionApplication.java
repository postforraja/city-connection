package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringBoot Application for city-connection
 */
@SpringBootApplication
@EnableSwagger2
public class ConnectionApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConnectionApplication.class, args);
	}
}
