package com.breaditnow.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoolSmsConfig {

    @Bean
    public DefaultMessageService messageService(
            @Value("${coolsms.api-key}") String apiKey,
            @Value("${coolsms.api-secret}") String apiSecret) {

        return NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }
}
