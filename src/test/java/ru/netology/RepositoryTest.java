package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class RepositoryTest {

    private Repository repository;

    @BeforeEach
    void setUp() {
        repository = new Repository(new Ticket[0]);
    }

    @Test
    void saveOneNewTicket() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        Ticket[] expected = new Ticket[]{new Ticket(10000L, "AAA", "BBB", 60)};
        Ticket[] actual = Arrays.stream(repository.findAll()).toArray(Ticket[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    void removeByIdMiddle() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        repository.removeById(repository.findAll()[1].getId());

        Ticket[] expected = new Ticket[]{
                new Ticket(10000L, "AAA", "BBB", 60),
                new Ticket(10002L, "EEE", "MMM", 65)};
        Ticket[] actual = Arrays.stream(repository.findAll()).toArray(Ticket[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    void removeByIdBegin() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        repository.removeById(repository.findAll()[0].getId());

        Ticket[] expected = new Ticket[]{
                new Ticket(10001L, "DDD", "CCC", 64),
                new Ticket(10002L, "EEE", "MMM", 65)};
        Ticket[] actual = Arrays.stream(repository.findAll()).toArray(Ticket[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    void removeByIdEnd() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        repository.removeById(repository.findAll()[2].getId());

        Ticket[] expected = new Ticket[]{
                new Ticket(10000L, "AAA", "BBB", 60),
                new Ticket(10001L, "DDD", "CCC", 64)};
        Ticket[] actual = Arrays.stream(repository.findAll()).toArray(Ticket[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    void removeByUnknownId() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            repository.removeById(100);
        });

        assertEquals("There is no ticket with id 100", exception.getMessage());
    }

    @Test
    void createByOtherRepo() {
        Ticket[] tmpRepo = new Ticket[]{
                new Ticket(10000L, "AAA", "BBB", 60),
                new Ticket(10001L, "DDD", "CCC", 64),
                new Ticket(10002L, "EEE", "MMM", 65)};

        repository = new Repository(tmpRepo);

        Ticket[] expected = new Ticket[]{
                new Ticket(10000L, "AAA", "BBB", 60),
                new Ticket(10001L, "DDD", "CCC", 64),
                new Ticket(10002L, "EEE", "MMM", 65)};
        Ticket[] actual = Arrays.stream(this.repository.findAll()).toArray(Ticket[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    void findByIdMiddle() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        Ticket ticket = new Ticket(10001L, "DDD", "CCC", 64);
        repository.save(ticket);
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        Ticket expected = new Ticket(10001L, "DDD", "CCC", 64);
        Ticket actual = (Ticket) repository.findById(ticket.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdBegin() {
        Ticket ticket = new Ticket(10000L, "AAA", "BBB", 60);
        repository.save(ticket);
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        Ticket expected = new Ticket(10000L, "AAA", "BBB", 60);
        Ticket actual = (Ticket) repository.findById(ticket.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdEnd() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        Ticket ticket = new Ticket(10002L, "EEE", "MMM", 65);
        repository.save(ticket);

        Ticket expected = new Ticket(10002L, "EEE", "MMM", 65);
        Ticket actual = (Ticket) repository.findById(ticket.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findByUnknownId() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        repository.save(new Ticket(10001L, "DDD", "CCC", 64));
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        Ticket expected = null;
        Ticket actual = (Ticket) repository.findById(100);

        assertEquals(expected, actual);
    }

    @Test
    void doubleAddTicket() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        Ticket ticket = new Ticket(10001L, "DDD", "CCC", 64);
        repository.save(ticket);
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            repository.save(ticket);
        });

        assertEquals("There is already a ticket with id " + ticket.getId(), exception.getMessage());
    }

    @Test
    void addSameId() {
        repository.save(new Ticket(10000L, "AAA", "BBB", 60));
        Ticket ticket = new Ticket(10001L, "DDD", "CCC", 64);
        repository.save(ticket);
        int id = ticket.getId();
        repository.save(new Ticket(10002L, "EEE", "MMM", 65));

        Ticket sameTicket = Mockito.mock(Ticket.class);
        doReturn(id).when(sameTicket).getId();

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            repository.save(sameTicket);
        });

        assertEquals("There is already a ticket with id " + id, exception.getMessage());
    }
}