package ru.job4j.concurrent;

/**
 * Class for concurrent running some threads.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class ConcurrentOutput {

    public static void main(String[] args) {
        Thread another0 = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread another1 = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another0.start();
        another1.start();
        System.out.println(Thread.currentThread().getName());
    }
}
