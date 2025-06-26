package com.breaditnow.owner.bakery.domain;

import com.breaditnow.bakery.domain.model.Address;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.model.Image;
import com.breaditnow.bakery.domain.model.PhoneNumber;
import com.breaditnow.common.exception.OwnerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.breaditnow.bakery.domain.model.OperatingStatus.*;
import static com.breaditnow.common.exception.OwnerErrorCode.BAKERY_INACTIVE;
import static com.breaditnow.common.exception.OwnerErrorCode.UNAUTHORIZED_BAKERY_ACCESS;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Bakery 도메인 테스트")
class BakeryTest {

    private final Long ownerId = 1L;
    private final Long otherOwnerId = 2L;
    private Bakery bakery;

    @BeforeEach
    void setUp() {
        bakery = Bakery.create(
                ownerId,
                "테스트빵집",
                Address.create("11000", "서울시 강남구 123-45", 37.123, 127.123),
                PhoneNumber.create("010-1234-5678"),
                Image.create("profile.jpg"),
                "09:00",
                "소개글"
        );
    }

    @Test
    @DisplayName("정상적으로 빵집을 생성한다")
    void createBakery() {
        assertThat(bakery.getOwnerId()).isEqualTo(ownerId);
        assertThat(bakery.getName()).isEqualTo("테스트빵집");
        assertThat(bakery.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("정상적으로 빵집 정보를 수정한다")
    void updateBakery() {
        bakery.update(ownerId, "수정된이름", "10:00", "수정된소개");
        assertThat(bakery.getName()).isEqualTo("수정된이름");
        assertThat(bakery.getOpenTime()).isEqualTo("10:00");
        assertThat(bakery.getIntroduction()).isEqualTo("수정된소개");
    }

    @Test
    @DisplayName("다른 소유자가 수정 시 예외 발생")
    void updateBakeryWithInvalidOwner() {
        assertThatThrownBy(() -> bakery.update(otherOwnerId, "이름", "10:00", "소개"))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED_BAKERY_ACCESS);
    }

    @Test
    @DisplayName("삭제된 빵집은 수정 불가")
    void updateBakeryWhenDeleted() {
        bakery.delete(ownerId);
        assertThatThrownBy(() -> bakery.update(ownerId, "이름", "10:00", "소개"))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", BAKERY_INACTIVE);
    }

    @Test
    @DisplayName("정상적으로 운영 상태를 변경한다")
    void updateOperatingStatus() {
        bakery.updateOperatingStatus(ownerId, CLOSED);
        assertThat(bakery.getOperatingStatus()).isEqualTo(CLOSED);
    }

    @Test
    @DisplayName("정상적으로 빵집을 삭제한다")
    void deleteBakery() {
        bakery.delete(ownerId);
        assertThat(bakery.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("다른 소유자가 삭제 시 예외 발생")
    void deleteBakeryWithInvalidOwner() {
        assertThatThrownBy(() -> bakery.delete(otherOwnerId))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED_BAKERY_ACCESS);
    }

    @Test
    @DisplayName("프로필 이미지를 정상적으로 변경한다")
    void updateProfileImage() {
        Image newImage = Image.create("new-profile.jpg");
        bakery.updateProfileImage(ownerId, newImage);
        assertThat(bakery.getProfileImage()).isEqualTo(newImage);
    }

    @Test
    @DisplayName("추가 이미지를 정상적으로 추가한다")
    void addAdditionalImages() {
        Image img1 = Image.create("a.jpg");
        Image img2 = Image.create("b.jpg");
        bakery.addAdditionalImages(ownerId, List.of(img1, img2));
        assertThat(bakery.getAdditionalImages()).contains(img1, img2);
    }

    @Test
    @DisplayName("삭제된 빵집에 이미지 추가 시 예외 발생")
    void addAdditionalImagesWhenDeleted() {
        bakery.delete(ownerId);
        Image img = Image.create("a.jpg");
        assertThatThrownBy(() -> bakery.addAdditionalImages(ownerId, List.of(img)))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", BAKERY_INACTIVE);
    }
}