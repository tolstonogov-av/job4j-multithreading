package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class for test class RolColSum.
 *
 * @author Tolstonogov Alexey
 */
public class RolColSumTest {

    private int[][] matrix = {
            {1,   2,  3,  4,  5},
            {6,   7,  8,  9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
    };

    private RolColSum.Sums[] expected = {
            new RolColSum.Sums(15, 55),
            new RolColSum.Sums(40, 60),
            new RolColSum.Sums(65, 65),
            new RolColSum.Sums(90, 70),
            new RolColSum.Sums(115, 75)
    };

    @Test
    public void whenSyncSumThenSumRowsAndColumns() {
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    public void whenASyncSumThenSumRowsAndColumns() {
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertArrayEquals(expected, result);
    }
}