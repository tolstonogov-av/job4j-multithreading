package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Synchronized collection.
 *
 * @param <T> type of item value in collection.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list = new ArrayList<>();

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * Creates fail-safe iterator.
     *
     * @return fail-safe iterator
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    /**
     * Copies iterator of "list" field.
     *
     * @param listToCopy collection with iterator to copy
     * @return copy of iterator from specified collection
     */
    private synchronized List<T> copy(List<T> listToCopy) {
        return List.copyOf(this.list);
    }
}
