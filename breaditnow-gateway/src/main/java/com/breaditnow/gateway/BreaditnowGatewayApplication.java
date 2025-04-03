package com.breaditnow.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.breaditnow.gateway",
	"com.breaditnow.logging"})
public class BreaditnowGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowGatewayApplication.class, args);
	}
}
