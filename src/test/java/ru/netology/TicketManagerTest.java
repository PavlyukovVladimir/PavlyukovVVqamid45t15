package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketManagerTest {

    private TicketManager manager;

    @BeforeEach
    void setUp() {
        manager = new TicketManager(new Repository(new Ticket[0]));
        manager.add(new Ticket(5000L, "ABC", "AZS", 100));
        manager.add(new Ticket(1000L, "BFR", "TYK", 60));
        manager.add(new Ticket(100L, "ABC", "MND", 500));
        manager.add(new Ticket(1000L, "ABC", "MNT", 300));
        manager.add(new Ticket(2000L, "UMN", "AZS", 150));
        manager.add(new Ticket(100L, "ABC", "MNT", 500));
        manager.add(new Ticket(3000L, "UMN", "AZS", 100));
    }

    // билеты добавлялись перед тестом, просто проверим что добавились
    @Test
    void addTicket() {
        Ticket[] expected = new Ticket[]{
                new Ticket(100L, "ABC", "MND", 500),
                new Ticket(100L, "ABC", "MNT", 500),
                new Ticket(1000L, "BFR", "TYK", 60),
                new Ticket(1000L, "ABC", "MNT", 300),
                new Ticket(2000L, "UMN", "AZS", 150),
                new Ticket(3000L, "UMN", "AZS", 100),
                new Ticket(5000L, "ABC", "AZS", 100)

        };

        Ticket[] actual = manager.searchBy("");

        assertArrayEquals(expected, actual);
    }

    @Test
    void searchBy() {
        Ticket[] expected = new Ticket[]{
                new Ticket(100L, "ABC", "MND", 500),
                new Ticket(100L, "ABC", "MNT", 500),
                new Ticket(1000L, "ABC", "MNT", 300),
                new Ticket(2000L, "UMN", "AZS", 150),
                new Ticket(3000L, "UMN", "AZS", 100)
        };

        Ticket[] actual = manager.searchBy("MN");

        assertArrayEquals(expected, actual);
    }

    @Test
    void findAll() {
        Ticket[] expected = new Ticket[]{
                new Ticket(100L, "ABC", "MNT", 500),
                new Ticket(1000L, "ABC", "MNT", 300),
        };

        Ticket[] actual = manager.findAll("ABC", "MNT");

        assertArrayEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        Ticket[] expected = new Ticket[0];

        Ticket[] actual = manager.findAll("ART", "MNT");

        assertArrayEquals(expected, actual);
    }

    @Test
    void findAllComparator() {
        Ticket[] expected = new Ticket[]{
                new Ticket(3000L, "UMN", "AZS", 100),
                new Ticket(2000L, "UMN", "AZS", 150)
        };

        Ticket[] actual = manager.findAll("UMN", "AZS", new TicketByMinutesOnWayAscComparator());

        assertArrayEquals(expected, actual);
    }

    @Test
    void findAllComparatorEmpty() {
        Ticket[] expected = new Ticket[0];

        Ticket[] actual = manager.findAll("ART", "MNT", new TicketByMinutesOnWayAscComparator());

        assertArrayEquals(expected, actual);
    }
}