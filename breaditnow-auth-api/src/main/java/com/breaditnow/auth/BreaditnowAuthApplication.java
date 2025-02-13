package com.breaditnow.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.breaditnow.auth",
	"com.breaditnow.redis"})
public class BreaditnowAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowAuthApplication.class, args);
	}
}
