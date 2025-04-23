package com.breaditnow.auth.global.security.oauth2.handler;

import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import com.breaditnow.auth.domain.email.service.EmailAuthService;
import com.breaditnow.auth.domain.email.service.EmailDuplicationService;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2EmailCodeListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final CustomerRepository customerRepo;
    private final EmailDuplicationService dupService;
    private final EmailAuthService emailAuthService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {

        if (!(e.getAuthentication().getPrincipal() instanceof DefaultOAuth2User oauth)) return;

        String oauth2Id = e.getAuthentication().getName();
        Customer customer = customerRepo.findByOauth2Id(oauth2Id).orElse(null);
        if (customer == null || !customer.isFirstLogin()) return;

        String email = extractEmail(oauth.getAttributes());
        if (email == null || dupService.isDuplicated(email)) return;

        emailAuthService.sendAuthCode(email, "CUSTOMER");
        log.info("first OAuth2 login – auth-code sent to {}", email);
    }

    private String extractEmail(Map<String, Object> attr) {
        if (attr.containsKey("kakao_account"))
            return (String)((Map<?,?>)attr.get("kakao_account")).get("email");
        if (attr.containsKey("response"))
            return (String)((Map<?,?>)attr.get("response")).get("email");
        return (String) attr.get("email");
    }
}
