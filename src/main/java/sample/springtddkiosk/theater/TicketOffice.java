package sample.springtddkiosk.theater;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TicketOffice {

    private int amount = 0;
    private final int ticketAmount = 10000;
    private final List<Ticket> tickets = new ArrayList<>();

    @Builder
    private TicketOffice(List<Ticket> tickets) {
        this.tickets.addAll(tickets);
    }

    public Ticket sellTicket(int amount) {
        validateTicketIsNotEmpty();
        this.amount += amount;
        return tickets.remove(0);
    }

    public Ticket exchangeToTicket(Invitation invitation) {
        validateTicketIsNotEmpty();
        validateNotUsedInvitation(invitation);
        invitation.use();
        return tickets.remove(0);
    }

    private void validateTicketIsNotEmpty() {
        if (tickets.isEmpty()) {
            throw new IllegalStateException("티켓이 모두 소진되었습니다");
        }
    }

    private static void validateNotUsedInvitation(Invitation invitation) {
        if (invitation.isUsed()) {
            throw new IllegalArgumentException("초대장을 교환할 수 없습니다");
        }
    }

}
