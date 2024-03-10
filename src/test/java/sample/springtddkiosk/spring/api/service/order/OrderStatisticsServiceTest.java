package sample.springtddkiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static sample.springtddkiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.springtddkiosk.spring.domain.product.ProductType.HANDMADE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import sample.springtddkiosk.spring.IntegrationTestSupport;
import sample.springtddkiosk.spring.domain.history.mail.MailSendHistory;
import sample.springtddkiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.springtddkiosk.spring.domain.order.Order;
import sample.springtddkiosk.spring.domain.order.OrderRepository;
import sample.springtddkiosk.spring.domain.order.OrderStatus;
import sample.springtddkiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.springtddkiosk.spring.domain.product.Product;
import sample.springtddkiosk.spring.domain.product.ProductRepository;
import sample.springtddkiosk.spring.domain.product.ProductType;

class OrderStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;
//    @MockBean
//    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 3, 6, 0, 0, 0);

        final Product product1 = createProduct("001", HANDMADE, 1000);
        final Product product2 = createProduct("002", HANDMADE, 3000);
        final Product product3 = createProduct("003", HANDMADE, 5000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 5, 23, 59, 59), products);
        Order order2 = createPaymentCompletedOrder(now, products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 6, 23, 59, 59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 7, 0, 0, 0), products);

        // stubbing
        given(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
            .willReturn(true);

        // when
        boolean result =
            orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 3, 6), "jihoi.kang@gmail.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 18000원입니다");
    }

    private Order createPaymentCompletedOrder(LocalDateTime registeredDate, List<Product> products) {
        Order order = Order.builder()
            .products(products)
            .orderStatus(OrderStatus.PAYMENT_COMPLETED)
            .registeredDate(registeredDate)
            .build();
        orderRepository.save(order);
        return order;
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