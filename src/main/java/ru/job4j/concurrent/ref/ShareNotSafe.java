package ru.job4j.concurrent.ref;

import java.util.stream.Collectors;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        User user1 = User.of("name1");
        cache.add(user);
        cache.add(user1);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                    user1.setName("rename1");
                }
        );
        first.start();
        first.join();
        System.out.println(cache
                .findAll().stream()
                .map(User::getName)
                .collect(Collectors.joining(", ")));
    }
}