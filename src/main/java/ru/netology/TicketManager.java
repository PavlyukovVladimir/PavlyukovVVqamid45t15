package ru.netology;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<Ticket> stream = Arrays.stream(findAll());
        return stream
                .filter(ticket -> ticket.getAirportFrom().contains(text) || ticket.getAirportTo().contains(text))
                .sorted()
                // терминальный метод потоков toArray без аргументов возвращает массив Object[], что не годится,
                // поэтому нужно либо отказаться от удобства потоков и фильтров,
                // либо использовать приведение массива объектов в массив билетов
                // .toArray(Ticket[]::new); - короткая форма, .toArray(size->new Ticket[size]); - длинная форма.

                // Eще можно как все нормальные люди отфильтровать ручками расширяя массив ответов каждый раз,
                // когда нашли подходящий по фильтру элемент.

                // Или принципиально отказаться от Массивов:
                // использовать списки и терминальный метод List<Ticket> lst = ... .collect(Collectors.toList())
                // и если надо вернуть массив то в конце: Ticket[] tmp = new Ticket(lst.size());
                // for(int i = 0; i < lst.size; i++){tmp[i] = lst.get(i);

                // грустно в общем
                .toArray(Ticket[]::new);
    }

    // Ищю билеты у которых соответствующие названия аэропортов совпадают с заданными
    public Ticket[] findAll(@NotNull String from, @NotNull String to) {
        Stream<Ticket> stream = Arrays.stream(findAll());
        return stream
                .filter(ticket -> from.equals(ticket.getAirportFrom()) && to.equals(ticket.getAirportTo()))
                .sorted()
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
