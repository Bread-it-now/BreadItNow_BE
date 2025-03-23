package com.breaditnow.customer.global.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FCMConfig {
	@Bean
	public FirebaseApp initializeFirebase() throws IOException {
		InputStream refreshToken = getClass().getClassLoader()
			.getResourceAsStream("firebase/breaditnow-fcm.json");

		if (refreshToken == null) {
			throw new IOException("File not found: serviceAccountKey.json");
		}

		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(refreshToken))
			.build();

		return FirebaseApp.initializeApp(options);
	}

	@Bean
	public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
		return FirebaseMessaging.getInstance(firebaseApp);
	}
}
