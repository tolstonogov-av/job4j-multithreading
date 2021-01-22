package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing class CASCount.
 *
 * @see ru.job4j.CASCount
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 *  */
public class CASCountTest {

    public final static int SIZE = 1000;

    @Test
    public void whenTwoThreadsUsedOneCASCountThenItWorkCorrect()
            throws InterruptedException {
        CASCount casCount = new CASCount();
        CASCountThread thread1 = new CASCountThread(casCount);
        CASCountThread thread2 = new CASCountThread(casCount);
        CASCountThread thread3 = new CASCountThread(casCount);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        assertEquals(SIZE * 3, casCount.get());
    }
}

/**
 * Thread, which run incrementing counter specified times.
 *
 * @see ru.job4j.CASCountTest#SIZE
 */
class CASCountThread extends Thread {

    private final CASCount casCount;

    public CASCountThread(CASCount casCount) {
        this.casCount = casCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < CASCountTest.SIZE; i++) {
            casCount.increment();
        }
    }
}
