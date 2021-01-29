package ru.job4j.caches;

public class OptimisticException extends RuntimeException {
    public OptimisticException() {
        super("Another user update model");
    }
}
