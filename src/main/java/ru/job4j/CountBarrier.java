package ru.job4j;

/**
 * Realizes wait of threads while counter reaches specified value.
 *
 * @author Tolstonofov Alexey, Job4j
 * @version 1.0
 */
public class CountBarrier {

    /**
     * Flag of reaches specified value.
     */
    private boolean flag = false;

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
            if (++count == total) {
                flag = true;
                monitor.notifyAll();
            }
        }
    }

    public void await() {
        synchronized (monitor) {
            if (!flag) {
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
            flag = false;
        }
    }
}