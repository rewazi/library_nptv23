package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.repository.InMemoryRepository;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.model.User;
import ee.ivkhkdev.model.Card;
import ee.ivkhkdev.services.AuthorService;
import ee.ivkhkdev.services.BookService;
import ee.ivkhkdev.services.UserService;
import ee.ivkhkdev.services.CardService;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.AppHelperAuthor;
import ee.ivkhkdev.AppHelperBook;
import ee.ivkhkdev.AppHelperUser;
import ee.ivkhkdev.AppHelperCard;

import java.util.ArrayList;
import java.util.List;

public class NPTV23Library {

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Card> cards = new ArrayList<>();

        // Создаем репозитории для хранения данных только в памяти
        FileRepository<Book> bookRepository = new InMemoryRepository<>();
        FileRepository<Author> authorRepository = new InMemoryRepository<>();
        FileRepository<User> userRepository = new InMemoryRepository<>();
        FileRepository<Card> cardRepository = new InMemoryRepository<>();

        // Создаем AppHelper и Service для авторов, книг, пользователей и карт
        AppHelperAuthor appHelperAuthor = new AppHelperAuthor(authorRepository, System.in);
        Service<Author> authorService = new AuthorService(authors, appHelperAuthor);

        AppHelperBook appHelperBook = new AppHelperBook(appHelperAuthor, authorService, bookRepository);
        Service<Book> bookService = new BookService(books, appHelperBook);

        AppHelperUser appHelperUser = new AppHelperUser(userRepository, System.in);
        Service<User> userService = new UserService(users, appHelperUser);

        AppHelperCard appHelperCard = new AppHelperCard(appHelperUser, userService, bookService, cardRepository);
        Service<Card> cardService = new CardService(cards, appHelperCard);

        // Запускаем приложение
        App app = new App(bookService, authorService, userService, cardService);
        app.run();
    }
}
