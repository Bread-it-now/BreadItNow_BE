package com.breaditnow.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.breaditnow.mysql"})
public class BreaditnowMysqlApplication {
	public static void main(String[] args) {
		SpringApplication.run(BreaditnowMysqlApplication.class, args);
	}
}
