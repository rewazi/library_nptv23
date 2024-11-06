package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.model.Card;
import ee.ivkhkdev.model.User;

import java.time.LocalDate;
import java.util.List;

public class AppHelperCard implements AppHelper<Card>, Input {
    private final Input input;
    private final Service<User> userService;
    private final Service<Book> bookService;
    private final FileRepository<Card> cardRepository;

    public AppHelperCard(Input input, Service<User> userService, Service<Book> bookService, FileRepository<Card> cardRepository) {
        this.input = input;
        this.userService = userService;
        this.bookService = bookService;
        this.cardRepository = cardRepository;
    }

    @Override
    public FileRepository<Card> getRepository() {
        return cardRepository;
    }

    @Override
    public Card create() {
        System.out.println("------ Создание новой карты выдачи ------");

        // Выбор пользователя
        User user = selectUser();
        if (user == null) {
            System.out.println("Некорректный выбор пользователя. Операция отменена.");
            return null;
        }

        // Выбор книги
        Book book = selectBook();
        if (book == null) {
            System.out.println("Некорректный выбор книги. Операция отменена.");
            return null;
        }

        // Запрашиваем дату выдачи с проверкой формата
        LocalDate issueDate;
        while (true) {
            System.out.print("Введите дату выдачи (YYYY-MM-DD): ");
            String inputDate = getString();
            try {
                issueDate = LocalDate.parse(inputDate); // Попытка преобразования
                break; // Если преобразование успешно, выходим из цикла
            } catch (Exception e) {
                System.out.println("Некорректный формат даты. Пожалуйста, введите дату в формате YYYY-MM-DD.");
            }
        }

        // Запрашиваем дату возврата с проверкой формата
        LocalDate returnDate;
        while (true) {
            System.out.print("Введите дату возврата (YYYY-MM-DD): ");
            String inputReturnDate = getString();
            try {
                returnDate = LocalDate.parse(inputReturnDate); // Попытка преобразования
                break; // Если преобразование успешно, выходим из цикла
            } catch (Exception e) {
                System.out.println("Некорректный формат даты. Пожалуйста, введите дату в формате YYYY-MM-DD.");
            }
        }

        // Создаем и возвращаем новую карточку
        return new Card(user, book, issueDate, returnDate);
    }

    // Метод для выбора пользователя
    private User selectUser() {
        userService.print();
        System.out.print("Выберите номер пользователя из списка: ");
        try {
            int userIndex = Integer.parseInt(getString()) - 1;
            return userService.list().get(userIndex);
        } catch (Exception e) {
            System.out.println("Ошибка: неверный ввод или выбранный номер пользователя не существует.");
            return null;
        }
    }

    // Метод для выбора книги
    private Book selectBook() {
        bookService.print();
        System.out.print("Выберите номер книги из списка: ");
        try {
            int bookIndex = Integer.parseInt(getString()) - 1;
            return bookService.list().get(bookIndex);
        } catch (Exception e) {
            System.out.println("Ошибка: неверный ввод или выбранный номер книги не существует.");
            return null;
        }
    }

    @Override
    public void printList(List<Card> cards) {
        System.out.println("------ Список карт выдачи ------");
        if (cards.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.printf("%d. Пользователь: %s %s, Книга: %s, Дата выдачи: %s, Дата возврата: %s%n",
                    i + 1,
                    card.getUser().getFirstname(),
                    card.getUser().getLastname(),
                    card.getBook().getTitle(),
                    card.getIssueDate(),
                    card.getReturnDate()
            );
        }
    }

    @Override
    public String getString() {
        return input.getString();
    }
}
