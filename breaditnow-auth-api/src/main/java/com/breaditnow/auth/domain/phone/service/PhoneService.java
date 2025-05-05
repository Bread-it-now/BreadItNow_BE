package com.breaditnow.auth.domain.phone.service;

import com.breaditnow.auth.global.exception.AuthException;
import com.breaditnow.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

import static com.breaditnow.auth.global.exception.AuthErrorCode.CODE_EXPIRED;
import static com.breaditnow.auth.global.exception.AuthErrorCode.CODE_MISMATCH;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneService {

    private final RedisRepository redis;
    private final DefaultMessageService messageService;

    @Value("${sms.sender}")
    private String sender;

    @Value("${sms.auth-code.ttl-seconds:180}")
    private long ttl;

    public SingleMessageSentResponse sendCode(String phone) {

        String code = "%06d".formatted(ThreadLocalRandom.current().nextInt(1_000_000));
        redis.save(key(phone), code, ttl * 1000);

        Message msg = new Message();
        msg.setFrom(sender);
        msg.setTo(phone);
        msg.setText("[빵잇나우] 인증코드 " + code + " (유효 " + ttl/60 + "분)");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(msg));
        return response;
    }

    public boolean verifyCode(String phone, String code) {
        String saved = (String) redis.find(key(phone))
                .orElseThrow(() -> new AuthException(CODE_EXPIRED));

        if (!saved.equals(code)) throw new AuthException(CODE_MISMATCH);

        redis.delete(key(phone));
        return true;
    }

    private String key(String p) { return "phone:auth:" + p; }
}
