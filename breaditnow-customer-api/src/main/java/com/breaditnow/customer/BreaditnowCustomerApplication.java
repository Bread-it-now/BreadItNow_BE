package com.breaditnow.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.breaditnow.customer", "com.breaditnow.mysql"})
@EnableJpaRepositories(basePackages = "com.breaditnow.mysql.repository")
public class BreaditnowCustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BreaditnowCustomerApplication.class, args);
    }
}