package com.breaditnow.customer.customer.domain.model;

import com.breaditnow.common.event.PasswordChangedEvent;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.customer.domain.port.out.PublishEventPort;
import com.breaditnow.customer.customer.domain.port.out.SaveImageStoragePort;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.common.util.ValidationUtils.setIfNotNull;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_NICKNAME;
import static com.breaditnow.customer.config.RabbitMQConfig.PASSWORD_CHANGE_ROUTING_KEY;

@Getter
public class Customer {
    private Long id;
    private String nickname;
    private String phone;
    private String profileImageUrl;
    private String fcmToken;

    @Builder
    public Customer(Long id, String nickname, String phone, String profileImageUrl, String fcmToken) {
        this.id = id;
        this.nickname = nickname;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
        this.fcmToken = fcmToken;
    }

    public void changeNickname(String nickname) {
        requireValid(nickname, String::isEmpty, () -> new CustomerException(INVALID_NICKNAME));
        this.nickname = nickname;
    }

    public void updateInfo(String nickname, String phone, String newPassword, MultipartFile profileImage, SaveImageStoragePort saveImageStoragePort, PublishEventPort publishEventPort) {
        setIfNotNull(profileImage, pi -> this.profileImageUrl = saveImageStoragePort.upload(pi, "image/customer/" + getId() + "/profile"));
        setIfNotNull(nickname, nn -> this.nickname = nn);
        setIfNotNull(phone, ph -> this.phone = ph);
        publishEventPort.publish(new PasswordChangedEvent(this.id, newPassword), PASSWORD_CHANGE_ROUTING_KEY);
    }
}
