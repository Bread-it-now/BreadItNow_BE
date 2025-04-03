package com.breaditnow.owner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.breaditnow.owner",
	"com.breaditnow.domain",
	"com.breaditnow.common",
	"com.breaditnow.external",
	"com.breaditnow.logging"
})
public class BreaditnowOwnerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowOwnerApplication.class, args);
	}
}
