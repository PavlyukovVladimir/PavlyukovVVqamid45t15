package ru.netology;

import java.util.Comparator;

public class TicketByMinutesOnWayAscComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.getMinutesOnWay() - o2.getMinutesOnWay();
    }
}
