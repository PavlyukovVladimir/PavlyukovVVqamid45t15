package ru.netology;

import org.jetbrains.annotations.NotNull;

// Интерфейс через который менеджеры могут работать с репозиториями
public interface Repositoric<T> {
    // добавить
    void save(@NotNull T element);

    // удалить
    void removeById(int itemId);

    // получить набор
    T[] findAll();

    T findById(int itemId);
}
