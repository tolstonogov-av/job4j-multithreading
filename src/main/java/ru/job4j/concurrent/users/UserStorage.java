package ru.job4j.concurrent.users;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> storage = new ConcurrentHashMap<>();

    public boolean add(User user) {
        boolean result = false;
        synchronized (this) {
            try {
                storage.put(user.getId(), User.of(user.getId(), user.getAmount()));
                result = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public boolean update(User user) {
        boolean result = false;
        synchronized (this) {
            if (storage.containsKey(user.getId())) {
                try {
                    storage.put(user.getId(), User.of(user.getId(), user.getAmount()));
                    result = true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }

    public boolean delete(User user) {
        boolean result = false;
        synchronized (this) {
            try {
                storage.remove(user.getId());
                result = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public User findById(int id) {
        User result = null;
        synchronized (this) {
            try {
                User userFound = storage.get(id);
                result = User.of(userFound.getId(), userFound.getAmount());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void transfer(int fromId, int toId, int amount) {
        try {
            User fromUser = findById(fromId);
            User toUser = findById(toId);
            if (fromUser.getAmount() >= amount) {
                synchronized (this) {
                    update(User.of(fromUser.getId(), fromUser.getAmount() - amount));
                    update(User.of(toUser.getId(), toUser.getAmount() + amount));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
