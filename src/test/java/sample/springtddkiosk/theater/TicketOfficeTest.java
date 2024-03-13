package sample.springtddkiosk.theater;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class TicketOfficeTest {

    @DisplayName("티켓을 판매합니다")
    @Test
    void sellTicket() {
        // given
        TicketOffice ticketOffice = createTicketOffice(List.of(Ticket.create()));

        // when
        ticketOffice.sellTicket(10000);

        // then
        assertThat(ticketOffice.getTickets()).isEmpty();
        assertThat(ticketOffice.getAmount()).isEqualTo(10000);
    }

    @DisplayName("남은 티켓이 없는데 티켓을 판매하면 에러가 발생합니다")
    @Test
    void sellTicketWhenTicketIsEmpty() {
        // given
        TicketOffice ticketOffice = createTicketOffice(List.of());

        // when & then
        Assertions.assertThatThrownBy(() -> ticketOffice.sellTicket(10000))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("티켓이 모두 소진되었습니다");
    }

    @DisplayName("초대장을 통해 티켓을 교환한다")
    @Test
    void exchangeToTicket() {
        // given
        TicketOffice ticketOffice = createTicketOffice(List.of(Ticket.create()));
        Invitation invitation = Invitation.create();

        // when
        ticketOffice.exchangeToTicket(invitation);

        // then
        assertThat(ticketOffice.getTickets()).isEmpty();
        assertThat(ticketOffice.getAmount()).isEqualTo(0);
        assertThat(invitation.isUsed()).isTrue();
    }

    private TicketOffice createTicketOffice(List<Ticket> tickets) {
        return TicketOffice.builder()
            .tickets(tickets)
            .build();
    }

}