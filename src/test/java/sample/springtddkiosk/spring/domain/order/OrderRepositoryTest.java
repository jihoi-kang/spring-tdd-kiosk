package sample.springtddkiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.springtddkiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.springtddkiosk.spring.domain.product.ProductType.HANDMADE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import sample.springtddkiosk.spring.IntegrationTestSupport;
import sample.springtddkiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.springtddkiosk.spring.domain.product.Product;
import sample.springtddkiosk.spring.domain.product.ProductRepository;
import sample.springtddkiosk.spring.domain.product.ProductType;

class OrderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("특정 날 결제 완료된 주문들을 조회한다")
    @Test
    void findOrdersBy() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 3, 6, 0, 0, 0);
        final Product product = createProduct("003", HANDMADE, 5000);
        List<Product> products = List.of(product);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 5, 23, 59, 59), products);
        Order order2 = createPaymentCompletedOrder(now, products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 6, 23, 59, 59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 7, 0, 0, 0), products);
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        // when *
        List<Order> orders = orderRepository.findOrdersBy(
            now,
            now.plusDays(1),
            OrderStatus.PAYMENT_COMPLETED
        );

        // then
        assertThat(orders).hasSize(2);
    }

    private Order createPaymentCompletedOrder(LocalDateTime registeredDate, List<Product> products) {
        return Order.builder()
            .products(products)
            .orderStatus(OrderStatus.PAYMENT_COMPLETED)
            .registeredDate(registeredDate)
            .build();
    }

    private Product createProduct(String productNumber, ProductType type, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(SELLING)
            .name("메뉴 이름")
            .price(price)
            .build();
    }

}