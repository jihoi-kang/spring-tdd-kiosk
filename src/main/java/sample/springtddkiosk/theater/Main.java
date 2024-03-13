package sample.springtddkiosk.theater;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Theater theater = new Theater();
        TicketOffice ticketOffice = TicketOffice.builder()
            .tickets(List.of(Ticket.create(), Ticket.create(), Ticket.create(), Ticket.create(), Ticket.create()))
            .build();
        Audience audience = Audience.builder()
            .invitation(Invitation.create())
            .build();
        audience.receiveTicket(ticketOffice);
        theater.enter(audience);
    }

}
