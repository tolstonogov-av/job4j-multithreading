package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

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
    private final DynamicContainer<T> list = new DynamicContainer<>();

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
     * Copies collection in "list" field.
     *
     * @param listToCopy collection with iterator to copy
     * @return copy of collection
     */
    private synchronized DynamicContainer<T> copy(DynamicContainer<T> listToCopy) {
        DynamicContainer<T> result = new DynamicContainer<>();
        listToCopy.forEach(result::add);
        return result;
    }
}
