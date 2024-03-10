package sample.springtddkiosk.spring.domain.stock;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import sample.springtddkiosk.spring.IntegrationTestSupport;

class StockRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품 번호들로 재고를 조회한다")
    @Test
    void findAllByProductNumbersIn() {
        // given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        final List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));


        // then
        Assertions.assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 1),
                tuple("002", 2)
            );
    }

}