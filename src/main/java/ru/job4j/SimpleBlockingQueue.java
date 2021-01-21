package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple bounded blocking queue.
 *
 * @author Tolstonogov Aleksey
 * @version 2.0
 *
 * @param <T> type of elements in queue
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    /**
     * Size to block the offer action.
     */
    private final int blockingSize;

    public SimpleBlockingQueue(int size) {
        this.blockingSize = size;
    }

    /**
     * Offers the element to queue.
     * If size of queue equals blocking size, thread enters to waiting state.
     * When it wakes up, it adds element to queue.
     *
     * @param value element to add
     */
    public synchronized void offer(T value) {
        try {
            while (queue.size() == blockingSize) {
                wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        queue.offer(value);
        notify();
    }

    /**
     * Polls the element of queue.
     * If queue is empty, the thread enters to wait state.
     * When it wakes up, it polls element from queue.
     *
     * @return element of queue
     */
    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        notify();
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}