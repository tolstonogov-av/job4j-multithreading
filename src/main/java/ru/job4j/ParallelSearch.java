package ru.job4j;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(
                () -> {
                    try {
                        Integer value;
                        boolean workingConsumer = true;
                        while (workingConsumer) {
                            value = queue.poll();
                            if (value == null) {
                                workingConsumer = false;
                            } else {
                                System.out.println(value);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(null);
                }
        ).start();
    }
}