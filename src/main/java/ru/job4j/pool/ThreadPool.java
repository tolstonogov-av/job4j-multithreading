package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for threads pool.
 *
 * @author Tolstonogov Aleksey
 * @version 1.0
 */
@ThreadSafe
public class ThreadPool {

    /**
     * Count of tasks for blocking queue.
     */
    private static final int TASKS_COUNT = 3;

    /**
     * Threads in pool.
     */
    private final List<Thread> threads = new LinkedList<>();

    /**
     * Queue with tasks.
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(TASKS_COUNT);

    public ThreadPool() {
        initThreads(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Create threads for pool with target, which run task from tasks, while not execute shutdown command.
     *
     * @param size Count of threads for pool
     */
    private void initThreads (int size) {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
    }

    /**
     * Push to tasks specified job.
     *
     * @param job job to push
     */
    public void work(Runnable job) {
        tasks.offer(job);
    }

    /**
     * Executing pool.
     */
    public void start() {
        threads.forEach(Thread::start);
    }

    /**
     * Shutdown the pool executing.
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}