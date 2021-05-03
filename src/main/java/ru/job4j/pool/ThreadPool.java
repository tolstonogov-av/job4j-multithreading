package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private static final int TASKS_COUNT = 3;

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(TASKS_COUNT);

    public ThreadPool() {
        for (int i = 0; i <= Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread());
        }
    }

    public void work(Runnable job) {

    }

    public void shutdown() {

    }
}