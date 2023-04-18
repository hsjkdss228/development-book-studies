package object.chapter1.procedural;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {
    private Long money;
    private List<Ticket> tickets;

    public TicketOffice(Long money, List<Ticket> tickets) {
        this.money = money;
        this.tickets = new ArrayList<>(tickets);
    }

    public Ticket ticket() {
        return tickets.remove(0);
    }

    public void minusMoney(Long money) {
        this.money -= money;
    }

    public void plusMoney(Long money) {
        this.money += money;
    }
}
