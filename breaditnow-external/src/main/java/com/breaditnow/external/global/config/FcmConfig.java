package com.breaditnow.external.global.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class FcmConfig {
	private final Resource firebaseResource;
	private final String projectId;

	public FcmConfig(ResourceLoader resourceLoader, @Value("${fcm.file_path}") String firebaseFilePath, @Value("${fcm.project_id}") String projectId) {
		Resource res = resourceLoader.getResource(firebaseFilePath);
		if (!res.exists()) {
			throw new IllegalStateException("FCM credential not found at: " + firebaseFilePath);
		}
		this.firebaseResource = res;
		this.projectId = projectId;
	}

	@PostConstruct
	public void init() throws IOException {
		FirebaseOptions option = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(firebaseResource.getInputStream()))
				.setProjectId(projectId)
				.build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(option);
		}
	}

	@Bean
	FirebaseMessaging firebaseMessaging() {
		return FirebaseMessaging.getInstance(firebaseApp());
	}

	@Bean
	FirebaseApp firebaseApp() {
		return FirebaseApp.getInstance();
	}
}
