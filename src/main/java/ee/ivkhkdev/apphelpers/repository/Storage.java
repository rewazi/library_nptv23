package ee.ivkhkdev.apphelpers.repository;

import ee.ivkhkdev.interfaces.FileRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage<T> implements FileRepository<T> {
    private final String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(T entity) {
        List<T> list = this.load();  // Загружаем существующие данные
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(entity);

        // Используем try-with-resources для автоматического закрытия потоков
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(list);
            objectOutputStream.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных в файл: " + fileName);
        }
    }

    @Override
    public List<T> load() {
        // Используем try-with-resources для автоматического закрытия потоков
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (List<T>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + fileName + ". Возвращается пустой список.");
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных из файла: " + fileName);
        } catch (ClassNotFoundException e) {
            System.out.println("Класс данных не найден при загрузке из файла: " + fileName);
        }
        return new ArrayList<>();  // Возвращаем пустой список в случае ошибки
    }
}
