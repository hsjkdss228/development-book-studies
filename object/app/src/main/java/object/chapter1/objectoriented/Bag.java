package object.chapter1.objectoriented;

public class Bag {
    private Long money;
    private Invitation invitation;
    private Ticket ticket;

    public Bag(Long money) {
        this.money = money;
        this.invitation = null;
    }

    public Long put(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        }
        setTicket(ticket);
        minusMoney(ticket.price());
        return ticket.price();
    }

    private boolean hasInvitation() {
        return invitation != null;
    }

    private boolean hasTicket() {
        return ticket != null;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusMoney(Long money) {
        this.money -= money;
    }

    private void plusMoney(Long money) {
        this.money += money;
    }
}
