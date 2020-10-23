package ru.job4j.concurrent;

/**
 * Simulating loading process for a specified time.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        int i = 0;
        boolean increase = true;
        System.out.print("\rLoading..." + process[i++]);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (i == 2 || i == 0) {
                increase = !increase;
            }
            System.out.print("\rLoading..." + process[increase ? i++ : i--]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2000);
        progress.interrupt();
    }
}
