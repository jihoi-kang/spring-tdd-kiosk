package sample.springtddkiosk.theater;

import static sample.springtddkiosk.theater.TicketStatus.BEFORE_ENTER;
import static sample.springtddkiosk.theater.TicketStatus.COMPLETE_ENTER;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Ticket {

    private TicketStatus status;

    void use() {
        if (status == COMPLETE_ENTER) {
            throw new IllegalStateException("사용할 수 없는 티켓입니다");
        }
        this.status = COMPLETE_ENTER;
    }

    @Builder
    private Ticket(TicketStatus status) {
        this.status = status;
    }

    public static Ticket create() {
        return Ticket.builder()
            .status(BEFORE_ENTER)
            .build();
    }
}
