package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Book;

import java.util.List;

public class BookService implements Service<Book> {

    private List<Book> books;
    private final AppHelper<Book> appHelperBook;


    public BookService(List<Book> books, AppHelper<Book> appHelperBook) {
        this.books = books;
        this.appHelperBook = appHelperBook;

    }

    @Override
    public boolean add() {
        try {
            Book book = appHelperBook.create();
            if (book == null) return false;

            books.add(book); // Добавляем в локальный список
            appHelperBook.getRepository().save(books); // Сохраняем в репозиторий

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean edit(Book book) {
        return false;
    }

    @Override
    public boolean remove(Book book) {
        return false;
    }

    @Override
    public void print() {
        appHelperBook.printList(this.list());
    }

    @Override
    public List<Book> list() {
        books = appHelperBook.getRepository().load(); // Загружаем из репозитория
        return books;
    }
}
