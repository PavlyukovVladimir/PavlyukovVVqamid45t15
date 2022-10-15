package ru.netology;

public class AlreadyExistsException extends RuntimeException {

    // Ошибка, которая сигнализирует о необходимости наличия чего либо
    public AlreadyExistsException(String message) {
        super(message);
    }
}
