package ru.job4j.synch;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for container for linked list.
 * @author Tolstonogov Alexey.
 * @version 1.0.
 * @param <E> Type of values, that containers contains.
 */
public class DynamicContainer<E> implements Iterable<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    /**
     * Counter of modifications.
     */
    private int modCount;

    /**
     * Adds new value to end of container.
     * @param value Value to add.
     */
    public void add(E value) {
        Node<E> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
        modCount++;
    }

    /**
     * Get value from container, which is located at the specified index.
     * @param index Location index.
     * @return Value, which is located at the index.
     */
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;
    }

    /**
     * Removes the value from container by specified index.
     * @param index Specified index from container, value by which will be deleted.
     */
    public void remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        Node<E> previousNode = node.previous;
        Node<E> nextNode = node.next;
        if (previousNode == null && nextNode == null) {
            first = null;
            last = null;
        } else if (previousNode == null) {
            nextNode.previous = null;
            first = nextNode;
        } else if (nextNode == null) {
            previousNode.next = null;
            last = previousNode;
        } else {
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }
        size--;
        modCount++;
    }

    /**
     * Returns size of container.
     * @return Size of container.
     */
    public int size() {
        return size;
    }

    /**
     * Returns iterator for container.
     * @return Iterator for container.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private int index;

            /**
             * Counter of modifications, which was store at start of iterations.
             */
            private int expectedModCount = modCount;

            /**
             * Contains the node for next iteration.
             */
            private Node<E> nextNode = first;


            /**
             * Checks whether the next element is there.
             * @return Result of check.
             * @throws ConcurrentModificationException if were modifications in container after iterator is creating.
             */
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            /**
             * Returns next node of containers.
             * @return Next node of containers.
             */
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E result = nextNode.data;
                nextNode = nextNode.next;
                index++;
                return result;
            }
        };
    }

    /**
     * Node of container.
     * @param <E> Type of value in each node.
     */
    private static class Node<E> {

        private E data;
        private Node<E> previous;
        private Node<E> next;

        public Node(Node<E> previous, E data, Node<E> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}
