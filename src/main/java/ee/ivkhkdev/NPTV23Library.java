package ee.ivkhkdev;

import ee.ivkhkdev.apphelpers.AppHelperAuthor;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.apphelpers.repository.Storage;
import ee.ivkhkdev.apphelpers.AppHelperBook;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.services.AuthorService;
import ee.ivkhkdev.services.BookService;
import ee.ivkhkdev.interfaces.Service;

import java.util.ArrayList;
import java.util.List;

public class NPTV23Library {

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        // Создаем репозитории для хранения данных
        FileRepository<Book> bookRepository = new Storage<>("books.dat");
        FileRepository<Author> authorRepository = new Storage<>("authors.dat");

        // Создаем AppHelper для авторов и книг с учетом входных данных
        AppHelperAuthor appHelperAuthor = new AppHelperAuthor(authorRepository, System.in);
        Service<Author> authorService = new AuthorService(authors, appHelperAuthor);
        AppHelperBook appHelperBook = new AppHelperBook(appHelperAuthor, authorService, bookRepository); // передаем appHelperAuthor как Input
        Service<Book> bookService = new BookService(books, appHelperBook);

        // Запускаем приложение
        App app = new App(bookService, authorService);
        app.run();
    }
}
