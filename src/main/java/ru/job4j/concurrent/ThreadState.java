package ru.job4j.concurrent;

/**
 * Display names of threads and message, when threads are finished.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(first.getName() + " and " + second.getName() + " are finished.");
    }
}
