package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.model.User;
import ee.ivkhkdev.model.Card;

import java.util.Scanner;

public class App implements Input {

    private final Service<Book> bookService;
    private final Service<Author> authorService;
    private final Service<User> userService;
    private final Service<Card> cardService;
    private final Scanner scanner = new Scanner(System.in); // Инициализация Scanner как поля класса

    public App(Service<Book> bookService, Service<Author> authorService, Service<User> userService, Service<Card> cardService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.userService = userService;
        this.cardService = cardService;
    }

    public void run() {
        System.out.println("------ Библиотека группы NPTV23 ------");
        System.out.println("--------------------------------------");
        boolean repeat = true;
        do {
            System.out.println("Список задач: ");
            System.out.println("0. Выйти из программы");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Список книг");
            System.out.println("3. Добавить автора");
            System.out.println("4. Добавить пользователя");
            System.out.println("5. Добавить карту выдачи");
            System.out.println("6. Список карт выдачи");
            System.out.println("7. Проверить просроченные карты");

            System.out.print("Введите номер задачи: ");
            int task = Integer.parseInt(getString());
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.println("----- Добавление книги -----");
                    if (bookService.add()) {
                        System.out.println("Книга добавлена");
                    } else {
                        System.out.println("Книгу добавить не удалось");
                    }
                    break;
                case 2:
                    System.out.println("----- Список книг -----");
                    bookService.print();
                    break;
                case 3:
                    System.out.println("----- Добавление автора -----");
                    if (authorService.add()) {
                        System.out.println("Автор добавлен");
                    } else {
                        System.out.println("Автора добавить не удалось");
                    }
                    break;
                case 4:
                    System.out.println("----- Добавление пользователя -----");
                    if (userService.add()) {
                        System.out.println("Пользователь добавлен");
                    } else {
                        System.out.println("Пользователя добавить не удалось");
                    }
                    break;
                case 5:
                    System.out.println("----- Добавление карты выдачи -----");
                    if (cardService.add()) {
                        System.out.println("Карта выдачи добавлена");
                    } else {
                        System.out.println("Карту добавить не удалось");
                    }
                    break;
                case 6:
                    System.out.println("----- Список карт выдачи -----");
                    cardService.print();
                    break;
                case 7:
                    System.out.println("----- Проверка просроченных карт -----");
                    checkOverdueCards();
                    break;
                default:
                    System.out.println("Выберите задачу из списка!");
            }
            System.out.println("--------------------------------------");
        } while (repeat);
        System.out.println("До свидания :)");
    }

    private void checkOverdueCards() {
        System.out.println("Проверка просроченных карт...");
        boolean foundOverdue = false;

        for (Card card : cardService.list()) {
            if (card.checkIfOverdue()) {
                System.out.println("Пользователь: " + card.getUser().getFirstname() + " " +
                        card.getUser().getLastname() + ", Книга: " + card.getBook().getTitle() + ", Штраф за просрочку: "
                        + calculateFine(card) + " руб.");
                foundOverdue = true;
            }
        }

        if (!foundOverdue) {
            System.out.println("Нет просроченных карт.");
        }
    }

    private double calculateFine(Card card) {
        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(card.getReturnDate(), java.time.LocalDate.now());
        return daysOverdue * 5.0; // Например, 5 рублей за день просрочки
    }

    @Override
    public String getString() {
        return scanner.nextLine();
    }
}
