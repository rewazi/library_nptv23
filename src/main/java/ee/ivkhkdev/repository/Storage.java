package ee.ivkhkdev.repository;

import ee.ivkhkdev.interfaces.FileRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage<T> implements FileRepository<T> {
    private final String filename;

    public Storage(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(T entity) {
        List<T> items = load();       // Загружаем текущие данные
        items.add(entity);             // Добавляем новый объект
        save(items);                   // Сохраняем обновленный список
    }

    @Override
    public void save(List<T> items) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(items);    // Записываем весь список объектов
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @Override
    public List<T> load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) in.readObject(); // Загружаем список объектов из файла
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();  // Возвращаем пустой список, если загрузка не удалась
        }
    }
}
