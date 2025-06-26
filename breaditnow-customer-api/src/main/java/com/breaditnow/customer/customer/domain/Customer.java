package com.breaditnow.customer.customer.domain;

import com.breaditnow.customer.customer.application.port.out.SaveImageStoragePort;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.common.util.ValidationUtils.setIfNotNull;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.*;

@Getter
public class Customer {
    private Long id;
    private String nickname;
    private String phone;
    private String profileImageUrl;
    private Provider provider;
    private String oauth2Id;
    private String email;
    private String password;
    private String fcmToken;
    private boolean initialSetup;

    @Builder
    public Customer(Long id, String nickname, String phone, String profileImageUrl, Provider provider, String oauth2Id, String email, String password, String fcmToken, boolean initialSetup) {
        this.id = id;
        this.nickname = nickname;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
        this.provider = provider;
        this.oauth2Id = oauth2Id;
        this.email = email;
        this.password = password;
        this.fcmToken = fcmToken;
        this.initialSetup = initialSetup;
    }

    public void changeNickname(String nickname) {
        if(this.initialSetup) {
            throw new CustomerException(ALREADY_INITIALIZED);
        }
        requireValid(nickname, String::isEmpty, () -> new CustomerException(INVALID_NICKNAME));
        this.nickname = nickname;
        this.initialSetup = true;
    }

    public void updateInfo(String nickname, String phone, String password, MultipartFile profileImage, SaveImageStoragePort saveImageStoragePort) {
        setIfNotNull(profileImage, pi -> this.profileImageUrl = saveImageStoragePort.upload(pi, "image/customer/" + getId() + "/profile"));
        setIfNotNull(password, pwd -> this.password = pwd);
        setIfNotNull(nickname, nn -> this.nickname = nn);
        setIfNotNull(phone, ph -> this.phone = ph);
    }

    public boolean isPasswordSame(PasswordEncoder passwordEncoder, String password) {
        requireValid(password, String::isEmpty, () -> new CustomerException(INVALID_PASSWORD));
        return passwordEncoder.matches(password, this.password);
    }
}
