package ru.netology;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Repository implements Repositoric<Identifiable> {
    private Identifiable[] repo;

    public Repository(@NotNull Identifiable[] repo) {
        this.repo = repo;
    }

    // добавить
    @Override
    public void save(@NotNull Identifiable element) {
        int id = element.getId();
        if (findById(id) != null) {
            throw new AlreadyExistsException("There is already a ticket with id " + id);
        }
        Identifiable[] tmp = Arrays.copyOf(repo, repo.length + 1);
        tmp[repo.length] = element;
        repo = tmp;
    }

    // удалить
    @Override
    public void removeById(int itemId) {
        for (int i = 0; i < repo.length; i++) {
            if (repo[i].getId() == itemId) {
                removeByIndex(i);
                return;
            }
        }
        throw new NotFoundException("There is no ticket with id " + itemId);
    }

    // получить набор билетов
    @Override
    public Identifiable[] findAll() {
        return repo.clone();
    }

    @Override
    public Identifiable findById(int itemId) {
        for (Identifiable element : repo) {
            if (element.getId() == itemId) {
                return element;
            }
        }
        return null;
    }

    @ExcludeFromJacocoGeneratedReport
    private void removeByIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index is %d, should be not negative", index));
        }
        if (index >= repo.length) {
            throw new IndexOutOfBoundsException(String.format("Index is %d, should be less then repository length %d", index, repo.length));
        }
        Identifiable[] tmp = new Ticket[repo.length - 1];
        int i = 0;
        for (; i < index; i++) {
            tmp[i] = repo[i];
        }
        i++;
        for (; i < repo.length; i++) {
            tmp[i - 1] = repo[i];
        }
        repo = tmp;
    }
}
