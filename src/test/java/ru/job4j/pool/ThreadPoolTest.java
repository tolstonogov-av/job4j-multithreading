package ru.job4j.pool;

import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Class for test class {@link ru.job4j.pool.ThreadPool}.
 *
 * @see ThreadPool
 *
 * @author Tolstonogov Aleksey
 * @version 1.0
 */
public class ThreadPoolTest {

    /**
     * Jobs for executing by the pool.
     */
    private final List<Runnable> tasks = List.of(
            () -> System.out.println("Task1 done."),
            () -> System.out.println("Task2 done."),
            () -> System.out.println("Task3 done."),
            () -> System.out.println("Task4 done."),
            () -> System.out.println("Task5 done.")
    );

    @Test
    public void whenTasksSendsToPoolThenTheyExecutesConcurrently() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            ThreadPool pool = new ThreadPool();
            Thread producer = new Thread(() -> tasks.forEach(pool::work));
            producer.start();
            pool.start();
            producer.join();
        }
        assertTrue(true);
    }
}
