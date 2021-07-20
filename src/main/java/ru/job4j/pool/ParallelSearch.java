package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

/**
 * Class for parallel search index of object in array with ForkJoinPool mechanism.
 *
 * @author Tolstonogov Alexey.
 */
public class ParallelSearch extends RecursiveTask<Integer> {

    /**
     * Array, in which the search is performed.
     */
    private final Object[] array;

    /**
     * Begin index for search.
     */
    private final int from;

    /**
     * End index for search.
     */
    private final int to;

    /**
     * Object that is being searched in array.
     */
    private final Object object;

    public ParallelSearch(Object[] array, Object object) {
        this.array = array;
        this.from = 0;
        this.to = array.length - 1;
        this.object = object;
    }

    public ParallelSearch(Object[] array, int from, int to, Object object) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.object = object;
    }

    /**
     * Creates two parallel and recursive threads to search in two halves of array.
     *
     * @return result of searching, if no result - -1
     */
    @Override
    protected Integer compute() {
        int result;
        if (to - from < 10) {
            result = linearSearch(array, from, to, object);
        } else {
            int mid = (from + to) / 2;
            ParallelSearch leftSearch = new ParallelSearch(array, from, mid, object);
            ParallelSearch rightSearch = new ParallelSearch(array, mid + 1, to, object);
            leftSearch.fork();
            rightSearch.fork();
            result = leftSearch.join();
            if (result == -1) {
                result = rightSearch.join();
            } else {
                rightSearch.cancel(true);
            }
        }
        return result;
    }

    /**
     * Linear serial search index in array.
     *
     * @param array array, in which the search is performed
     * @param from begin index for search
     * @param to end index for search
     * @param object object that is being searched in array
     * @return result of searching, if no result - -1
     */
    private int linearSearch(Object[] array, int from, int to, Object object) {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(object)) {
                result = i;
                break;
            }
        }
        return result;
    }
}
