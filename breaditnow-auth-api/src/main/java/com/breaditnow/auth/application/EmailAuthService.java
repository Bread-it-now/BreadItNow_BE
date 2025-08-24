package com.breaditnow.auth.application;

import com.breaditnow.auth.domain.port.out.EmailAuthCodeRepository;
import com.breaditnow.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static com.breaditnow.common.exception.AuthErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAuthService {
    private final EmailDuplicationService dupService;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final JavaMailSender mailSender;

    @Value("${mail.auth-code.ttl-seconds:300}")
    private long ttlSeconds;

    @Value("${mail.auth-code.subject}")
    private String subject;

    @Value("${mail.auth-code.body-template}")
    private String bodyTemplate;

    @Transactional
    public void sendAuthCode(String email, String role) {
        if (dupService.isDuplicated(email)) {
            throw new AuthException(EMAIL_ALREADY_EXISTS);
        }

        String code = "%06d".formatted(ThreadLocalRandom.current().nextInt(1_000_000));
        emailAuthCodeRepository.save(email, code, Duration.ofSeconds(ttlSeconds));

        String body = bodyTemplate
                .replace("{code}", code)
                .replace("{ttl}", String.valueOf(ttlSeconds / 60));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void verifyCode(String email, String code) {
        String saved = emailAuthCodeRepository.find(email)
                .orElseThrow(() -> new AuthException(CODE_EXPIRED));

        if (!saved.equals(code)) {
            throw new AuthException(CODE_MISMATCH);
        }

        emailAuthCodeRepository.delete(email);
    }
}
