package com.breaditnow.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.breaditnow.domain"})
public class BreaditnowDomainApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowDomainApplication.class, args);
	}
}
