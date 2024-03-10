package sample.springtddkiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import static sample.springtddkiosk.spring.domain.product.ProductType.BAKERY;
import static sample.springtddkiosk.spring.domain.product.ProductType.BOTTLE;
import static sample.springtddkiosk.spring.domain.product.ProductType.HANDMADE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ProductTypeTest {

    @DisplayName("핸드메이드 상품은 재고와 관련된 상품이 아니다")
    @Test
    void handmadeProductContainsStockType() {
        // given
        ProductType givenType = HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 재고와 관련된 상품인지를 확인한다")
    @Test
    void containsStockType() {
        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다")
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    @ParameterizedTest
    void containsStockType1(ProductType productType, boolean expected) {
        // given

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
            Arguments.of(HANDMADE, false),
            Arguments.of(BOTTLE, true),
            Arguments.of(BAKERY, true)
        );
    }

    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containsStockType2(ProductType productType, boolean expected) {
        // given

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

}