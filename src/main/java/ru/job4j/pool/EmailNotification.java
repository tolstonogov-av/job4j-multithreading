package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for sending emails to users.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class EmailNotification {

    /**
     * Pool, that perform the sending emails.
     */
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * Queues email to specified user.
     *
     * @param user user, to whom email is sending
     */
    public void emailTo(User user) {
        pool.submit(() -> send(
                String.format("Notification %s to email %s", user.getUsername(), user.getEmail()),
                String.format("Add a new event to %s", user.getUsername()),
                user.getEmail()));
    }

    /**
     * Sends the email to specified user.
     *
     * @param subject subject of email
     * @param body body of email
     * @param email email address
     */
    public void send(String subject, String body, String email) {
    }

    /**
     * Finishes the email jobs and waits for started email is done.
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns whether the all jobs is done.
     *
     * @return true, if all jobs is done, otherwise - false
     */
    public boolean allEmailsSent() {
        return pool.isTerminated();
    }
}
