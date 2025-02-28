package com.breaditnow.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.customer.global.security.AuthCustomer;

@RestController
@RequestMapping("/api/")
public class CustomerCheckController {

	@Value("${server.port}")
	private String port;

	@GetMapping("/check")
	public String check(@AuthCustomer Long customerId) {
		return String.format("Customer API on PORT %s, customer=%d", port, customerId);
	}
}
