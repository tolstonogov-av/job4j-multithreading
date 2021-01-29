package ru.job4j.caches;

import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {

    /**
     * Key - Base.id, value - Base.
     */
    private final ConcurrentHashMap<Integer, Base> tasks = new ConcurrentHashMap<>();

    public Base add(Base model) {
        return tasks.put(model.getId(), model);
    }

    /**
     * Base.version++ on update.
     * Two users at the same time update the same one Base.
     *
     * @param model model with new data
     * @return updated model
     */
    public Base update(Base model) {
        Base currentModel = tasks.get(model.getId());
        if (currentModel != null) {
            int currentVersion = currentModel.getVersion();
            tasks.computeIfPresent(model.getId(), (integer, base) -> {
                if (base.getVersion() != currentVersion) {
                    throw new OptimisticException();
                }
                base.setName(model.getName());
                base.setVersion(currentVersion + 1);
                return base;
            });
        }
        return currentModel;
    }

    /**
     * @param model model to remove
     * @return removed model
     */
    public Base delete(Base model) {
        return tasks.remove(model.getId());
    }
}
