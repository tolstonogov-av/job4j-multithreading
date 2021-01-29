package ru.job4j.caches;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class NonBlockingCacheTest {

    @Test
    public void whenUpdateIn2ThreadsSimultaneouslyThanThrowExceptionInOne() throws InterruptedException {
        AtomicReference<Exception> exception = new AtomicReference<>();
        NonBlockingCache cache = new NonBlockingCache();
        cache.add(new Base(1, "task1"));
        Thread thread1 = new Thread(() -> {
            try {
                cache.update(new Base(1, "task1_"));
            } catch (Exception e) {
                exception.set(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                cache.update(new Base(1, "task1__"));
            } catch (Exception e) {
                exception.set(e);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        String result = exception.get() == null ? "" : exception.get().getMessage();
        assertEquals("Another user update model", result);
    }
}
