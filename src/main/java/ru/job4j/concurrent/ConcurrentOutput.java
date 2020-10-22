package ru.job4j.concurrent;

public class ConcurrentOutput {

    public static void main(String[] args) {
        Thread another0 = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread another1 = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another0.start();
        another1.run();
        System.out.println(Thread.currentThread().getName());
    }
}
