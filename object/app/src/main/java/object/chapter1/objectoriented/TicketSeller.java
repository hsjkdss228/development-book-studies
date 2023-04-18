package object.chapter1.objectoriented;

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        Ticket ticket = ticketOffice.ticket();
        Long income = audience.buy(ticket);
        ticketOffice.plusMoney(income);
    }
}
