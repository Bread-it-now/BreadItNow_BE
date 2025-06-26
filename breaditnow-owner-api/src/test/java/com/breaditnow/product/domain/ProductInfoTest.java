package com.breaditnow.product.domain;

import com.breaditnow.image.domain.Image;
import com.breaditnow.product.domain.model.ProductInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductInfo 도메인 테스트")
class ProductInfoTest {

    @Test
    @DisplayName("정상적으로 ProductInfo를 생성한다")
    void createProductInfo() {
        // given
        String name = "크루아상";
        String description = "바삭하고 고소한 크루아상";
        Image image = Image.create("croissant.jpg");

        // when
        ProductInfo productInfo = ProductInfo.create(name, description, image);

        // then
        assertThat(productInfo.name()).isEqualTo(name);
        assertThat(productInfo.description()).isEqualTo(description);
        assertThat(productInfo.profileImage()).isEqualTo(image);
    }

    @Test
    @DisplayName("프로필 이미지가 있을 때 getProfileImageUrl()은 이미지 URL을 반환한다")
    void getProfileImageUrlWithImage() {
        // given
        Image image = Image.create("bread.jpg");
        ProductInfo productInfo = ProductInfo.create("식빵", "부드러운 식빵", image);

        // when & then
        assertThat(productInfo.getProfileImageUrl()).isEqualTo("bread.jpg");
    }

    @Test
    @DisplayName("프로필 이미지가 없을 때 getProfileImageUrl()은 null을 반환한다")
    void getProfileImageUrlWithoutImage() {
        // given
        ProductInfo productInfo = ProductInfo.create("바게트", "프랑스식 바게트", null);

        // when & then
        assertThat(productInfo.getProfileImageUrl()).isNull();
    }
}