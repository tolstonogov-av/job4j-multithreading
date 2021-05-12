package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class for threads pool.
 *
 * @author Tolstonogov Aleksey
 * @version 2.0
 */
@ThreadSafe
public class ThreadPool {

    /**
     * Count of threads for pool.
     */
    private static final int TASKS_POOL = Runtime.getRuntime().availableProcessors();

    /**
     * Threads in pool.
     */
    private final List<Thread> threads = new LinkedList<>();

    /**
     * Queue with tasks.
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(TASKS_POOL);

    /**
     * Create threads for pool with target, which run task from tasks, while not execute shutdown command.
     */
    public void start () {
        IntStream.range(0, TASKS_POOL).forEach(el -> {
            var thread = new Thread(() -> {
                while (true) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            thread.start();
            threads.add(thread);
        });
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
     * Interrupts the pool executing.
     */
    public void shutdown() {
        while (!tasks.isEmpty()) {
        }
        threads.forEach(Thread::interrupt);
    }
}
