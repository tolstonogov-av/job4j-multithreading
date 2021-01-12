package ru.job4j;

/**
 * Realizes wait of threads while counter reaches specified value.
 *
 * @author Tolstonofov Alexey, Job4j
 * @version 2.0
 */
public class CountBarrier {

    private final Object monitor = this;

    /**
     * Bound of the counter.
     */
    private final int total;

    /**
     * Counter.
     */
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Increases counter and check reaches the value.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count != total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void reset() {
        synchronized (monitor) {
            count = 0;
        }
    }
}