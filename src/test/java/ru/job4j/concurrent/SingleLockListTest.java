package ru.job4j.concurrent;

import org.junit.Test;
import ru.job4j.synch.SingleLockList;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        for (int iteration = 1; iteration <= 50000; iteration++) {
            SingleLockList<Integer> list = new SingleLockList<>();
            Thread first = new Thread(() -> list.add(1));
            Thread second = new Thread(() -> list.add(2));
            Thread third = new Thread(() -> list.add(3));
            Thread fourth = new Thread(() -> list.add(4));
            Thread fifth = new Thread(() -> list.add(5));
            first.start();
            second.start();
            third.start();
            fourth.start();
            fifth.start();
            first.join();
            second.join();
            third.join();
            fourth.join();
            fifth.join();
            Set<Integer> rsl = new TreeSet<>();
            list.iterator().forEachRemaining(rsl::add);
            assertEquals(Set.of(2, 4, 3, 5, 1), rsl);
        }
    }
}
