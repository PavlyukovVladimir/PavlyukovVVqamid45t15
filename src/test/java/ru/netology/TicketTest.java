package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    @Test
    public void ticketNegativeCost() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(-1L, "AAA", "AAA", 1);
        });
        assertEquals(String.format("Cost of ticket is %d, should be not negative", -1L), exception.getMessage());
    }

    @Test
    public void ticketNegativeMinutes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1L, "AAA", "AAA", -1);
        });
        assertEquals(String.format("Minutes on the way is %d, should be positive", -1), exception.getMessage());
    }

    @Test
    public void ticketZeroMinutes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1L, "AAA", "AAA", 0);
        });
        assertEquals(String.format("Minutes on the way is %d, should be positive", 0), exception.getMessage());
    }

    @Test
    public void ticketNullAirportFrom() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Ticket(1L, null, "AAA", 1);
        });
        assertEquals("The AirportFrom must be not null", exception.getMessage());
    }

    @Test
    public void ticketNullAirportTo() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Ticket(1L, "AAA", null, 1);
        });
        assertEquals("The AirportTo must be not null", exception.getMessage());
    }

    @Test
    public void ticketZeroCostInKopecks() {
        Ticket ticket = new Ticket(0, "AAA", "BBB", 1);
        long expected = 0;
        long actual = ticket.getCostInKopecks();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketZeroCostInRub() {
        Ticket ticket = new Ticket(0, "AAA", "BBB", 1);

        double expected = 0.0;
        double actual = ticket.getCostInRub();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketCostInKopecks() {
        Ticket ticket = new Ticket(701L, "AAA", "BBB", 1);

        long expected = 701L;
        long actual = ticket.getCostInKopecks();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketCostInRub() {
        Ticket ticket = new Ticket(701L, "AAA", "BBB", 1);

        double expected = 7.01;
        double actual = ticket.getCostInRub();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketAirportFrom() {
        Ticket ticket = new Ticket(701L, "AAA", "BBB", 1);

        String expected = "AAA";
        String actual = ticket.getAirportFrom();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketAirportTo() {
        Ticket ticket = new Ticket(701L, "AAA", "BBB", 1);

        String expected = "BBB";
        String actual = ticket.getAirportTo();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketMinutesOnWay() {
        Ticket ticket = new Ticket(701L, "AAA", "BBB", 1);

        int expected = 1;
        int actual = ticket.getMinutesOnWay();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketEqualsCompareTo() {
        Ticket ticket1 = new Ticket(701L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "CCC", "DDD", 110);

        int expected = 0;
        int actual = ticket1.compareTo(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketMoreCompareTo() {
        Ticket ticket1 = new Ticket(702L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "AAA", "BBB", 10);

        int expected = 1;
        int actual = ticket1.compareTo(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketLessCompareTo() {
        Ticket ticket1 = new Ticket(700L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "AAA", "BBB", 10);

        int expected = -1;
        int actual = ticket1.compareTo(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketId() {
        Ticket ticket1 = new Ticket(700L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(700L, "AAA", "BBB", 10);

        int expected = ticket1.getId() + 1;
        int actual = ticket2.getId();
        assertEquals(expected, actual);
    }

    @Test
    public void ticketEqualsOtherTicket() {
        Ticket ticket1 = new Ticket(700L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(700L, "AAA", "BBB", 10);

        boolean expected = true;
        boolean actual = ticket1.equals(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketEqualsSelf() {
        Ticket ticket1 = new Ticket(700L, "AAA", "BBB", 10);

        boolean expected = true;
        boolean actual = ticket1.equals(ticket1);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketNotEqualsOtherType() {
        Ticket ticket1 = new Ticket(700L, "AAA", "BBB", 10);

        boolean expected = false;
        boolean actual = ticket1.equals("ticket");
        assertEquals(expected, actual);
    }

    @Test
    public void ticketCostNotEquals() {
        Ticket ticket1 = new Ticket(701L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(700L, "AAA", "BBB", 10);

        boolean expected = false;
        boolean actual = ticket1.equals(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketAirportFromNotEquals() {
        Ticket ticket1 = new Ticket(701L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "CCC", "BBB", 10);

        boolean expected = false;
        boolean actual = ticket1.equals(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketAirportToNotEquals() {
        Ticket ticket1 = new Ticket(701L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "AAA", "CCC", 10);

        boolean expected = false;
        boolean actual = ticket1.equals(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketMinutesOnWayNotEquals() {
        Ticket ticket1 = new Ticket(701L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "AAA", "BBB", 101);

        boolean expected = false;
        boolean actual = ticket1.equals(ticket2);
        assertEquals(expected, actual);
    }

    @Test
    public void ticketToString() {
        Ticket ticket1 = new Ticket(701L, "AAA", "BBB", 10);
        Ticket ticket2 = new Ticket(701L, "AAA", "BBB", 10);

        String expected = "Ticket{id: " +
                (ticket1.getId() + 1) +
                ", cost in RUB: 7.01, airport from: AAA, airport to: BBB, minutes on way: 10}";
        String actual = ticket2.toString();
        assertEquals(expected, actual);
    }
}