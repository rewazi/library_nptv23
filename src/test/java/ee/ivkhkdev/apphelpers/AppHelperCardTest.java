package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.AppHelperCard;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.model.Card;
import ee.ivkhkdev.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppHelperCardTest {

    private Input inputMock;
    private Service<User> userServiceMock;
    private Service<Book> bookServiceMock;
    private FileRepository<Card> cardRepositoryMock;
    private AppHelperCard appHelperCard;

    @BeforeEach
    void setUp() {
        inputMock = mock(Input.class);
        userServiceMock = mock(Service.class);
        bookServiceMock = mock(Service.class);
        cardRepositoryMock = mock(FileRepository.class);

        appHelperCard = new AppHelperCard(inputMock, userServiceMock, bookServiceMock, cardRepositoryMock);
    }

    @Test
    void testCreateCardSuccessful() {
        // Создаем и настраиваем пользователя с именем и фамилией
        User user = new User();
        user.setFirstname("Иван");
        user.setLastname("Иванов");

        // Создаем и настраиваем книгу с названием
        Book book = new Book();
        book.setTitle("Программирование на Java");

        // Настраиваем список пользователей и книг для мока
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userServiceMock.list()).thenReturn(users);

        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookServiceMock.list()).thenReturn(books);

        // Настраиваем последовательность ответов для вызовов getString()
        when(inputMock.getString()).thenReturn("1", "1", "2023-10-01", "2023-10-10"); // выбор пользователя, книги, даты выдачи и возврата

        // Вызываем метод create() для создания карточки
        Card card = appHelperCard.create();

        // Проверки
        assertNotNull(card, "Card должна быть успешно создана");
        assertEquals(user, card.getUser(), "Пользователь должен совпадать");
        assertEquals(book, card.getBook(), "Книга должна совпадать");
        assertEquals(LocalDate.of(2023, 10, 1), card.getIssueDate(), "Дата выдачи должна совпадать");
        assertEquals(LocalDate.of(2023, 10, 10), card.getReturnDate(), "Дата возврата должна совпадать");
    }

    @Test
    void testCreateCardWithInvalidUser() {
        // Настраиваем некорректный ввод пользователя
        when(userServiceMock.list()).thenReturn(new ArrayList<>()); // пустой список пользователей
        when(inputMock.getString()).thenReturn("1");

        Card card = appHelperCard.create();

        assertNull(card, "Card должна быть null при некорректном выборе пользователя");
    }

    @Test
    void testCreateCardWithInvalidBook() {
        // Создаем пользователя и устанавливаем имя и фамилию
        User user = new User();
        user.setFirstname("Иван");
        user.setLastname("Иванов");

        List<User> users = new ArrayList<>();
        users.add(user);
        when(userServiceMock.list()).thenReturn(users);
        when(inputMock.getString()).thenReturn("1"); // выбор пользователя

        // Настраиваем некорректный ввод книги
        when(bookServiceMock.list()).thenReturn(new ArrayList<>()); // пустой список книг
        when(inputMock.getString()).thenReturn("1");

        Card card = appHelperCard.create();

        assertNull(card, "Card должна быть null при некорректном выборе книги");
    }

    @Test
    void testPrintList() {
        List<Card> cards = new ArrayList<>();

        // Создаем пользователя, книгу и карточку
        User user = new User();
        user.setFirstname("Иван");
        user.setLastname("Иванов");

        Book book = new Book();
        book.setTitle("Программирование на Java");

        LocalDate issueDate = LocalDate.of(2023, 10, 1);
        LocalDate returnDate = LocalDate.of(2023, 10, 10);
        Card card = new Card(user, book, issueDate, returnDate);
        cards.add(card);

        appHelperCard.printList(cards);

        // Проверяем, что вывод списка карт вызывает вывод для каждой карточки
        verify(inputMock, never()).getString(); // Проверяем, что getString не вызывается в printList
    }
}
