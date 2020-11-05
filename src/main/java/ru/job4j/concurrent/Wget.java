package ru.job4j.concurrent;

/**
 * Load simulation.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.print("\rLoading: 0%");
                        for (int i = 1; i <= 100; i++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading: " + i + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
