package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.AppHelperBook;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.interfaces.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppHelperBookTest {

    private Input inputMock;
    private Service<Author> authorServiceMock;
    private FileRepository<Book> bookRepositoryMock;
    private AppHelperBook appHelperBook;
    private List<Author> authors;

    @BeforeEach
    void setUp() {
        inputMock = mock(Input.class);
        authorServiceMock = mock(Service.class);
        bookRepositoryMock = mock(FileRepository.class);

        // Создаем список авторов для теста
        authors = new ArrayList<>();
        authors.add(new Author("Иван", "Иванов"));
        authors.add(new Author("Александр", "Петров"));

        // Настраиваем моки
        when(authorServiceMock.list()).thenReturn(authors);

        appHelperBook = new AppHelperBook(inputMock, authorServiceMock, bookRepositoryMock);
    }

    @Test
    void testGetRepository() {
        assertEquals(bookRepositoryMock, appHelperBook.getRepository(), "Должен возвращать bookRepository");
    }

    @Test
    void testCreateBook() {
        // Настраиваем ввод данных
        when(inputMock.getString()).thenReturn("Книга по Java", "1", "n", "2023");

        Book book = appHelperBook.create();

        assertNotNull(book, "Книга не должна быть null");
        assertEquals("Книга по Java", book.getTitle(), "Название книги должно совпадать");
        assertEquals(2023, book.getPublishedYear(), "Год издания должен совпадать");
        assertEquals(1, book.getAuthors().size(), "Должен быть один автор");
        assertEquals("Иван", book.getAuthors().get(0).getAuthorName(), "Имя автора должно совпадать");
        assertEquals("Иванов", book.getAuthors().get(0).getAuthorSurname(), "Фамилия автора должна совпадать");
    }

    @Test
    void testPrintList() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setTitle("Книга по Java");
        book.setPublishedYear(2023);
        book.getAuthors().add(new Author("Иван", "Иванов"));
        books.add(book);

        appHelperBook.printList(books);

        // Проверяем, что информация о книге выводится корректно
        verify(inputMock, times(0)).getString(); // getString не должен вызываться при printList
    }
}
