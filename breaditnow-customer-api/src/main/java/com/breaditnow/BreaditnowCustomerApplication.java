package com.breaditnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BreaditnowCustomerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowCustomerApplication.class, args);
	}
}
