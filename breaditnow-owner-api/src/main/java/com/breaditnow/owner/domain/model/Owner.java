package com.breaditnow.owner.domain.model;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Owner {
    private Long id;
    private String fcmToken;
    private String nickname;

    public static Owner create(Long ownerId) {
        return Owner.builder()
                .id(ownerId)
                .build();
    }

    @Builder
    private Owner(Long id, String fcmToken, String nickname) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.nickname = nickname;
    }

    public void changeFcmToken(String token) {
        this.fcmToken = token;
    }

    public void initialize(String nickname) {
        this.nickname = nickname;
    }

    public boolean isInitialized() {
        return StringUtils.isNotBlank(this.nickname);
    }
}