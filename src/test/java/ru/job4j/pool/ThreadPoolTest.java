package ru.job4j.pool;

import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    private final List<Runnable> tasks = List.of(
            () -> System.out.println("Task1 done."),
            () -> System.out.println("Task2 done."),
            () -> System.out.println("Task3 done."),
            () -> System.out.println("Task4 done."),
            () -> System.out.println("Task5 done.")
    );

//    private final ThreadPool pool = new ThreadPool();

    @Test
    public void whenTasksSendToPoolThenTheyExecuteConcurrently() throws InterruptedException {
//        for (int i = 1; i <= 100; i++) {
        ThreadPool pool = new ThreadPool();
        Thread producer = new Thread(() -> tasks.forEach(pool::work));
        pool.start();
        producer.start();
        producer.join();
//        }
//        tasks.forEach(pool::work);
    }
}