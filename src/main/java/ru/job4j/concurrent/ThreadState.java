package ru.job4j.concurrent;

/**
 * Display names of threads and message, when threads are finished.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("first state: " + first.getState());
            System.out.println("second state: " + second.getState());
        }
        System.out.println(first.getName() + " and " + second.getName() + " are finished.");
    }
}
