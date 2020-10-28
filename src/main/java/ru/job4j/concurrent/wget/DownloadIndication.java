package ru.job4j.concurrent.wget;

/**
 * Indicates the progress of process.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class DownloadIndication {
    private final char[] process = {'\\', '|', '/'};

    private int i;

    private boolean increase;

    public void printLoading() {
        if (i == 2 || i == 0) {
            increase = !increase;
        }
        System.out.print("\rLoading..." + process[increase ? i++ : i--]);
    }
}
