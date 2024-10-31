package ee.ivkhkdev.interfaces;

import java.util.List;

public interface FileRepository<T> {
    void save(T entity);
    List<T> load();
}
