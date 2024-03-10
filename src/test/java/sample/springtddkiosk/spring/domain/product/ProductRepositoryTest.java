package sample.springtddkiosk.spring.domain.product;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sample.springtddkiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sample.springtddkiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.springtddkiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static sample.springtddkiosk.spring.domain.product.ProductType.HANDMADE;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import sample.springtddkiosk.spring.IntegrationTestSupport;

class ProductRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다")
    @Test
    void findAllBySellingStatusIn() {
        // given
        final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        final Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        final Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        final List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        Assertions.assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            );
    }

    @DisplayName("상품 번호들로 상품들을 조회한다")
    @Test
    void findAllByProductNumbersIn() {
        // given
        final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        final Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        final Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        final List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        Assertions.assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            );
    }

    @DisplayName("가장 마지막으로 저장한 상품 번호를 불러온다")
    @Test
    void findLatestProductNumber() {
        // given
        final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        final Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        String targetProductNumber = "003";
        final Product product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        final String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        Assertions.assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽을 때 상품이 하나도 없는 경우 null을 반환한다")
    @Test
    void findLatestProductNumberWhenProductIsEmpty() {
        // given

        // when
        final String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        Assertions.assertThat(latestProductNumber).isNull();
    }

    private static Product createProduct(
        String productNumber,
        ProductType type,
        ProductSellingStatus sellingStatus,
        String name,
        int price
    ) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }


}