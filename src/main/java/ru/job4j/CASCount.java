package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Class for count, which work on CAS-operations.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
@ThreadSafe
public class CASCount {

    /**
     * "Compare And Swap" counter.
     */
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer expected;
        do {
            expected = count.get();
        } while (!count.compareAndSet(expected, expected + 1));
    }

    public int get() {
        return count.get();
    }
}
