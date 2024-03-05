package sample.springtddkiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @DisplayName("재고가 있는 제품인지 확인한다")
    @Test
    void isQuantityLessThan() {
        // given
        Stock stock = Stock.create("001", 2);

        // when
        boolean isQuantityLessThan = stock.isQuantityLessThan(3);

        // then
        assertThat(isQuantityLessThan).isTrue();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다")
    @Test
    void deductQuantity() {
        // given
        Stock stock = Stock.create("001", 2);

        // when
        stock.deductQuantity(1);

        // then
        assertThat(stock.getQuantity()).isEqualTo(1);
    }

    @DisplayName("재고보다 많은 수의 수량을 차감 시도하는 경우 예외가 발생한다")
    @Test
    void deductQuantityException() {
        // given
        Stock stock = Stock.create("001", 2);

        // when & then
        Assertions.assertThatThrownBy(() -> stock.deductQuantity(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고가 없습니다");
    }

}