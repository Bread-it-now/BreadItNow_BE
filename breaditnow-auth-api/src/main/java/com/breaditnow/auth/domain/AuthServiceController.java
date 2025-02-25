package com.breaditnow.auth.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AuthServiceController {

	@Value("${server.port}")
	private String port;

	@GetMapping("/check")
	public String check() {
		return String.format("Auth Service on PORT %s", port);
	}
}
