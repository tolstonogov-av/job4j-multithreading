package ru.job4j.pool;

import org.junit.Test;

import java.util.ArrayList;
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

    @Test
    public void whenTasksSendsToPoolThenTheyExecutesConcurrently() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            final List<Integer> sum = new ArrayList<>();
            ThreadPool pool = new ThreadPool();
            List<Runnable> tasks = List.of(
                    () -> sum.add(2),
                    () -> sum.add(4),
                    () -> sum.add(8),
                    () -> sum.add(16),
                    () -> sum.add(32)
            );
            Thread producer = new Thread(() -> tasks.forEach(pool::work));
            producer.start();
            pool.start();
            producer.join();
            pool.shutdown();
            int sum_expected = 0;
            for (int s : sum) {
                sum_expected += s;
            }
            assertEquals(2 + 4 + 8 + 16 + 32, sum_expected);
        }
    }
}
