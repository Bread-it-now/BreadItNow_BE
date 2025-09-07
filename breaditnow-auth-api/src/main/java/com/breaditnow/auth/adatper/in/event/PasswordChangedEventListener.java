package com.breaditnow.auth.adatper.in.event;

import com.breaditnow.auth.application.port.in.ChangePasswordUseCase;
import com.breaditnow.common.event.PasswordChangedEvent;
import com.breaditnow.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.CommonErrorCode.RABBITMQ_COMMUNICATION_ERROR;
import static com.breaditnow.config.RabbitMQConfig.PASSWORD_CHANGE_QUEUE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordChangedEventListener {
    private final ChangePasswordUseCase changePasswordUseCase;

    @RabbitListener(queues = PASSWORD_CHANGE_QUEUE_NAME)
    public void handlePasswordChange(PasswordChangedEvent event) {
        try {
            changePasswordUseCase.changePassword(event.getAccountId(), event.getNewPassword());
        } catch (Exception e) {
            log.error("비밀번호 변경 처리 중 오류 발생: accountId={}, error={}", event.getAccountId(), e.getMessage());
            throw new AuthException(RABBITMQ_COMMUNICATION_ERROR);
        }
    }
}
