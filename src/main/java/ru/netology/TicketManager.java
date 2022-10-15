package ru.netology;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Predicate;

public class TicketManager {

    private final Repository repository;

    public TicketManager(Repository repository) {
        this.repository = repository;
    }

    // добавляю билет в репозиторий
    public void add(@NotNull Ticket ticket) {
        repository.save(ticket);
    }

    // Ищю билеты в которых искомая строка содержится хотя бы в одном названии аэропорта
    public Ticket[] searchBy(@NotNull String text) {
        Predicate<Identifiable> p = element -> {
            Ticket ticket = (Ticket) element;
            return ticket.getAirportFrom().contains(text) || ticket.getAirportTo().contains(text);
        };
        return Arrays.stream(repository.findAll()).filter(p).map(x -> (Ticket) x).sorted().toArray(Ticket[]::new);
    }

    // Ищю билеты у которых соответствующие названия аэропортов совпадают с заданными
    public Ticket[] findAll(@NotNull String from, @NotNull String to) {
        Predicate<Identifiable> p = element -> {
            Ticket ticket = (Ticket) element;
            return from.equals(ticket.getAirportFrom()) && to.equals(ticket.getAirportTo());
        };
        return Arrays.stream(repository.findAll()).filter(p).map(x -> (Ticket) x).sorted().toArray(Ticket[]::new);
    }
}
