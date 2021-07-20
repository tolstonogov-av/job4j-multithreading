package ru.job4j.pool;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Class for test class {@link ru.job4j.pool.ParallelSearch}.
 *
 * @author Tolstonogov Alexey
 */
public class ParallelSearchTest {

    private final int endRangeExcl = 101;

    /**
     * {100, 99, ..., 2, 1}
     */
    private final Integer[] array = IntStream.range(1, endRangeExcl)
            .map(i -> endRangeExcl - i)
            .boxed()
            .toArray(Integer[]::new);

    @Test
    public void whenSearchIndexInArrayByObjectThenParallelSearch() {
        Integer object = 6;
        int index = new ForkJoinPool().invoke(new ParallelSearch(array, object));
        int expected = endRangeExcl - 1 - object;
        assertEquals(expected, index);
    }
}