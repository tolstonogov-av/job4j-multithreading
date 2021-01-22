package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Tests methods of class SimpleBlockingQueue.
 *
 * @author Tolstonogov Aleksey
 * @version 2.0
 */
public class SimpleBlockingQueueTest {

    @Test
    public void whenTwoThreadsConsumeAndProduceThenBlockingQueueNewTest() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> IntStream.range(0, 5).forEach(queue::offer));
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        consumer.join();
        assertEquals(List.of(0, 1, 2, 3, 4), buffer);
    }
}