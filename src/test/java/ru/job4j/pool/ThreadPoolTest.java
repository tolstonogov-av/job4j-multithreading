package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

/**
 * Class for test class {@link ru.job4j.pool.ThreadPool}.
 *
 * @see ThreadPool
 *
 * @author Tolstonogov Aleksey
 * @version 2.0
 */
public class ThreadPoolTest {

    @Test
    public void whenTasksSendsToPoolThenTheyExecutesConcurrently() {
        var result = new AtomicBoolean(false);
        var pool = new ThreadPool();
        pool.start();
        pool.work(() -> result.set(true));
        pool.shutdown();
        assertTrue(result.get());
    }
}
