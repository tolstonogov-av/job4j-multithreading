package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Class, that contains sync and async methods sums in matrix.
 *
 * @author Tolstonogov Alexey
 */
public class RolColSum {

    /**
     * Contains sums by row and column.
     */
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    /**
     * Calculates sums by rows and columns, using sync.
     *
     * @param matrix array for calculating
     * @return array with Sums
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Sums(Arrays.stream(matrix[i]).sum(), sumCol(matrix, i));
        }
        return result;
    }

    /**
     * Calculates sums by rows and columns, using async.
     *
     * @param matrix array for calculating
     * @return array with Sums
     */
    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] result = new Sums[matrix[0].length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < result.length; i++) {
            futures.put(i, getSums(matrix, i));
        }
        for (int i : futures.keySet()) {
            try {
                result[i] = futures.get(i).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Creates async task for calculating sums by specified row and specified column.
     *
     * @param matrix array for calculating
     * @param rowColNumber number of row and column to calculating
     * @return async task
     */
    private static CompletableFuture<Sums> getSums(int[][] matrix, int rowColNumber) {
        return CompletableFuture.supplyAsync(() ->
                new Sums(Arrays.stream(matrix[rowColNumber]).sum(), sumCol(matrix, rowColNumber)));
    }

    /**
     * Calculates sum by specified column.
     *
     * @param matrix array for calculating
     * @param col number of column to calculating sum
     * @return sum of elements in specified column
     */
    private static int sumCol(int[][] matrix, int col) {
        int result = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            result += matrix[i][col];
        }
        return result;
    }
}