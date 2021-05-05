package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private static final int TASKS_COUNT = 3;

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(TASKS_COUNT);

    private boolean softShutdown = false;

    public ThreadPool() {
        initThreads(Runtime.getRuntime().availableProcessors());
    }

    private void initThreads (int size) {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!softShutdown) {
                    try {
                        Runnable task = tasks.poll();
                        System.out.println(Thread.currentThread().getName() + " task get");
                        task.run();
                        System.out.println(Thread.currentThread().getName() + " task done");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
    }

    public void work(Runnable job) {
        System.out.println("Start offer job.");
        tasks.offer(job);
        System.out.println("Finish offer job.");
    }

    public void start() {
        threads.forEach(Thread::start);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public void join() {
        this.softShutdown = true;
    }
}