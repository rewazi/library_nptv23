package ee.ivkhkdev.services;

import ee.ivkhkdev.App;
import ee.ivkhkdev.interfaces.BookProvider;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Book;

public class BookService {

    public final BookProvider bookProvider;

    public BookService(BookProvider bookProvider) {
        this.bookProvider = bookProvider;
    }

    public boolean add(Input input) {
        Book book = bookProvider.create(input);
        for (int i = 0; i < App.books.length; i++) {
            if (App.books[i] == null) {
                App.books[i] = book;
                System.out.println("Книга добавлена");
                return true;
            }
        }
        return false;
    }

    public String printListBooks() {
        return bookProvider.getList();
    }
}