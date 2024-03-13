package sample.springtddkiosk.theater;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TicketTest {

    @DisplayName("티켓을 등록할 때 초기화 상태는 입장전 상태입니다")
    @Test
    void init() {
        // given
        Ticket ticket = Ticket.create();

        // when

        // then
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.BEFORE_ENTER);
    }

    @DisplayName("티켓을 사용하면 상태는 입장완료가 됩니다")
    @Test
    void use() {
        // given
        Ticket ticket = Ticket.create();

        // when
        ticket.use();

        // then
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.COMPLETE_ENTER);
    }

    @DisplayName("티켓 상태가 완료되면 입장할 수 없습니다")
    @Test
    void useWhenCompleteEnter() {
        // given
        Ticket ticket = Ticket.builder()
            .status(TicketStatus.COMPLETE_ENTER)
            .build();

        // when & then
        assertThatThrownBy(ticket::use)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("사용할 수 없는 티켓입니다");
    }

}