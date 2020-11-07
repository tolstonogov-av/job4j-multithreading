package ru.job4j.concurrent.users;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserStorageTest {

    @Test
    public void whenTransfer50FromUser1ToUser2TwiseThen50And250() throws InterruptedException {
        for (int iteration = 0; iteration <= 5000; iteration++) {
            User user1 = User.of(1, 1500);
            User user2 = User.of(2, 1500);
            UserStorage storage = new UserStorage();
            storage.add(user1);
            storage.add(user2);
            Thread thread1 = new TransferThread(storage, user1, user2);
            Thread thread2 = new TransferThread(storage, user1, user2);
            Thread thread3 = new TransferThread(storage, user1, user2);
            Thread thread4 = new TransferThread(storage, user1, user2);
            Thread thread5 = new TransferThread(storage, user1, user2);
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            assertEquals(1250, storage.findById(user1.getId()).getAmount());
            assertEquals(1750, storage.findById(user2.getId()).getAmount());
        }
    }

    private static class TransferThread extends Thread {
        private final UserStorage storage;

        private final User user1;

        private final User user2;

        public TransferThread(UserStorage storage, User user1, User user2) {
            this.storage = storage;
            this.user1 = user1;
            this.user2 = user2;
        }

        @Override
        public void run() {
            storage.transfer(user1.getId(), user2.getId(), 50);
        }
    }
}