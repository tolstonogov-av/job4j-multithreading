package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CountBarrierTest {

    List<String> threadsSequence = new ArrayList<>();

    @Test
    public void whenIncrementCountThenThreadsWaitUntilCountEqualsTotal() throws InterruptedException {
        CountBarrier barrier = new CountBarrier(1);
        for (int i = 0; i < 5000; i++) {
            threadsSequence.clear();
            Thread thread1 = new Thread(() -> {
                barrier.await();
                threadsSequence.add(Thread.currentThread().getName());
            },
                    "thread1");
            Thread thread2 = new Thread(() -> {
                threadsSequence.add(Thread.currentThread().getName());
                barrier.count();
            },
                    "thread2");
            thread1.start();
            thread2.start();
            thread1.join();
            thread1.join();
            barrier.reset();
            assertEquals(List.of("thread2", "thread1"), threadsSequence);
        }
    }
}