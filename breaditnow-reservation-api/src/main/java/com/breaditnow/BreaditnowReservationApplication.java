package com.breaditnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BreaditnowReservationApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowReservationApplication.class, args);
	}
}
