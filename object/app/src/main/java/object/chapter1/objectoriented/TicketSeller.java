package object.chapter1.objectoriented;

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        if (audience.bag().hasInvitation()) {
            Ticket ticket = ticketOffice.ticket();
            audience.bag().setTicket(ticket);
            return;
        }
        Ticket ticket = ticketOffice.ticket();
        audience.bag().minusMoney(ticket.price());
        ticketOffice.plusMoney(ticket.price());
        audience.bag().setTicket(ticket);
    }
}
