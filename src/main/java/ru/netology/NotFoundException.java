package ru.netology;

public class NotFoundException extends RuntimeException {

    // ошибка, которая сигнализирует что что-то не найдено
    public NotFoundException(String message) {
        super(message);
    }
}
