package ee.ivkhkdev;


import ee.ivkhkdev.apphelpers.AppHelper;
import ee.ivkhkdev.apphelpers.AppHelperAuthor;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.apphelpers.AppHelperBook;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.services.AuthorService;
import ee.ivkhkdev.services.BookService;
import ee.ivkhkdev.services.Service;

import java.util.ArrayList;
import java.util.List;


public class NPTV23Library {

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        Input input = new ConsoleInput();
        AppHelper<Author> appHelperAuthor = new AppHelperAuthor(input);
        Service<Author> authorService = new AuthorService(authors, appHelperAuthor);
        AppHelper<Book> appHelperBook = new AppHelperBook(input,authorService);
        Service<Book> bookService = new BookService(books,appHelperBook);
        App app = new App(input, bookService, authorService);
        app.run();
    }

}