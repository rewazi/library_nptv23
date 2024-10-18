package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.UserProvider;
import ee.ivkhkdev.interfaces.impl.AppHelperUsers;
import ee.ivkhkdev.services.BookService;
import ee.ivkhkdev.interfaces.BookProvider;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.impl.ConsoleInput;
import ee.ivkhkdev.interfaces.impl.AppHelperBooks;
import ee.ivkhkdev.services.UserService;

public class NPTV23Library {

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        BookProvider bookProvider = new AppHelperBooks();
        BookService bookService = new BookService(bookProvider);
        UserProvider userProvider = new AppHelperUsers();
        UserService userService = new UserService(userProvider);
        App app = new App(input,bookService,userService);
        app.run();
    }

}