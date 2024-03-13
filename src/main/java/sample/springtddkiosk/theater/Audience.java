package sample.springtddkiosk.theater;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Audience {

    private int amount;
    private final Ticket ticket;
    private final Invitation invitation;

    @Builder
    private Audience(int amount, Ticket ticket, Invitation invitation) {
        this.amount = amount;
        this.ticket = ticket;
        this.invitation = invitation;
    }

    public void receiveTicket(TicketOffice ticketOffice) {
        if (hasInvitation()) {
            ticketOffice.exchangeToTicket(invitation);
        } else {
            ticketOffice.sellTicket(ticketOffice.getTicketAmount());
            minusAmount(ticketOffice.getTicketAmount());
        }
    }

    public void useTicket() {
        if (ticket != null) {
            ticket.use();
        }
    }

    private boolean hasInvitation() {
        return invitation != null;
    }

    private void minusAmount(int amount) {
        if (this.amount < amount) {
            throw new IllegalArgumentException("가지고 있는 돈이 더 적습니다");
        }
        this.amount -= amount;
    }

}
