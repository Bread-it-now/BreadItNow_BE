package com.breaditnow.owner.bakery.domain;

import com.breaditnow.owner.global.exception.OwnerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Address 도메인 테스트")
class AddressTest {

    @Nested
    @DisplayName("생성")
    class Creation {

        @Test
        @DisplayName("정상적으로 생성된다")
        void createSuccessfully() {
            Address address = Address.create("11000", "서울시 강남구 123-45", 37.123, 127.123);
            assertThat(address.regionCode()).isEqualTo("11000");
            assertThat(address.fullAddress()).isEqualTo("서울시 강남구 123-45");
            assertThat(address.latitude()).isEqualTo(37.123);
            assertThat(address.longitude()).isEqualTo(127.123);
        }

        @Test
        @DisplayName("regionCode가 비어있으면 예외 발생")
        void throwExceptionWhenRegionCodeIsEmpty() {
            assertThatThrownBy(() -> Address.create("", "서울시 강남구 123-45", 37.123, 127.123))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", REGION_CODE_REQUIRED);
        }

        @Test
        @DisplayName("fullAddress가 비어있으면 예외 발생")
        void throwExceptionWhenFullAddressIsEmpty() {
            assertThatThrownBy(() -> Address.create("11000", "", 37.123, 127.123))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", FULL_ADDRESS_REQUIRED);
        }

        @Test
        @DisplayName("위도/경도 범위가 벗어나면 예외 발생")
        void throwExceptionWhenGeoLocationIsInvalid() {
            assertThatThrownBy(() -> Address.create("11000", "서울시 강남구 123-45", -91.0, 127.123))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GEOLOCATION_NOT_FOUND);

            assertThatThrownBy(() -> Address.create("11000", "서울시 강남구 123-45", 37.123, -181.0))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GEOLOCATION_NOT_FOUND);

            assertThatThrownBy(() -> Address.create("11000", "서울시 강남구 123-45", 91.0, 127.123))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GEOLOCATION_NOT_FOUND);

            assertThatThrownBy(() -> Address.create("11000", "서울시 강남구 123-45", 37.123, 181.0))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GEOLOCATION_NOT_FOUND);
        }
    }
}