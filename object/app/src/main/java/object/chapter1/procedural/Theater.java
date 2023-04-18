package object.chapter1.procedural;

public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        if (audience.bag().hasInvitation()) {
            Ticket ticket = ticketSeller.ticketOffice().ticket();
            audience.bag().setTicket(ticket);
            return;
        }
        Ticket ticket = ticketSeller.ticketOffice().ticket();
        audience.bag().minusMoney(ticket.price());
        ticketSeller.ticketOffice().plusMoney(ticket.price());
        audience.bag().setTicket(ticket);
    }
}
