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
        try {
            do {
                expected = count.get();
            } while (!count.compareAndSet(expected, expected + 1));
        } catch (UnsupportedOperationException e) {
            Thread.currentThread().interrupt();
            throw new UnsupportedOperationException("Count couldn't be implemented.");
        }
    }

    public int get() {
        try {
            return count.get();
        } catch (UnsupportedOperationException e) {
            Thread.currentThread().interrupt();
            throw new UnsupportedOperationException("Count value couldn't be got.");
        }
    }
}
