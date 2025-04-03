package com.breaditnow.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.breaditnow.customer",
	"com.breaditnow.domain",
	"com.breaditnow.common",
	"com.breaditnow.external",
	"com.breaditnow.logging"})
public class BreaditnowCustomerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowCustomerApplication.class, args);
	}
}
