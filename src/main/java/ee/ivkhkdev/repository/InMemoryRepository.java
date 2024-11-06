package ee.ivkhkdev.repository;

import ee.ivkhkdev.interfaces.FileRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository<T> implements FileRepository<T> {
    private final List<T> items = new ArrayList<>();

    @Override
    public void save(T entity) {

    }

    @Override
    public void save(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    @Override
    public List<T> load() {
        return new ArrayList<>(items); // Возвращаем копию списка, чтобы предотвратить изменение оригинала
    }
}
