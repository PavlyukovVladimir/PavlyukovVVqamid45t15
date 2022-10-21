package ru.netology;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;

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
        return Arrays.stream(findAll())
                .filter(ticket -> ticket.getAirportFrom().contains(text) || ticket.getAirportTo().contains(text))
                .sorted()
                .toArray(Ticket[]::new);
    }

    // Ищю билеты у которых соответствующие названия аэропортов совпадают с заданными
    public Ticket[] findAll(@NotNull String from, @NotNull String to) {
        return Arrays.stream(findAll())
                .filter(ticket -> from.equals(ticket.getAirportFrom()) && to.equals(ticket.getAirportTo()))
                .sorted()
                .toArray(Ticket[]::new);
    }

    public Ticket[] findAll(@NotNull String from, @NotNull String to, @NotNull Comparator<Ticket> comparator) {
        return Arrays.stream(findAll())
                .filter(ticket -> from.equals(ticket.getAirportFrom()) && to.equals(ticket.getAirportTo()))
                .sorted(comparator)
                .toArray(Ticket[]::new);
    }

    public Ticket[] findAll() {
        // репозиторий хранит массив Identifiable, поэтому в менеджере билетов, элементы нужно приводить к Ticket явно
        Identifiable[] allElements = repository.findAll();
        Ticket[] tmp = new Ticket[allElements.length];
        for (int i = 0; i < allElements.length; i++) {
            tmp[i] = (Ticket) allElements[i];
        }
        return tmp;
    }
}
