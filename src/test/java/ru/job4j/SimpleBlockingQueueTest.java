package ru.job4j;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests methods of class SimpleBlockingQueue.
 *
 * @author Tolstonogov Aleksey
 * @version 1.0
 */
public class SimpleBlockingQueueTest {

    /**
     * Simple bounded blocking queue to test.
     */
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);

    @Test
    public void whenTwoThreadsConsumeAndProduceThenBlockingQueue() throws InterruptedException {
        List<Integer> result = new LinkedList<>();
        Thread consumer = new Thread(() -> {
            result.add(queue.poll());
            result.add(queue.poll());
            result.add(queue.poll());
        });
        Thread producer = new Thread(() -> {
            queue.offer(10);
            queue.offer(30);
            queue.offer(20);
            queue.offer(50);
        });
        consumer.start();
        producer.start();
        consumer.join();
        consumer.join();
        assertEquals(List.of(10, 30, 20), result);
    }
}