package ru.netology;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
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
        return Arrays.stream(repository.findAll())
                .filter(p)
                .map(x -> (Ticket) x)
                .sorted()
                .toArray(Ticket[]::new);
    }

    // вынес общую часть фильтрующую билеты из методов findAll
    private boolean predicat(Identifiable element, String from, String to) {
        Ticket ticket = (Ticket) element;
        return from.equals(ticket.getAirportFrom()) && to.equals(ticket.getAirportTo());
    }

    // Ищю билеты у которых соответствующие названия аэропортов совпадают с заданными
    public Ticket[] findAll(@NotNull String from, @NotNull String to) {
        return Arrays.stream(repository.findAll())
                .filter(element -> predicat(element, from, to))
                .map(x -> (Ticket) x)
                .sorted()
                .toArray(Ticket[]::new);
    }

    public Ticket[] findAll(@NotNull String from, @NotNull String to, @NotNull Comparator<Ticket> comparator) {
        return Arrays.stream(repository.findAll())
                .filter(element -> predicat(element, from, to))
                .map(x -> (Ticket) x)
                .sorted(comparator)
                .toArray(Ticket[]::new);
    }
}
