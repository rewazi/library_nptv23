package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private List<Book> books;
    private AppHelper<Book> appHelperMock;
    private BookService bookService;
    private FileRepository<Book> fileRepositoryMock;

    @BeforeEach
    void setUp() {
        books = new ArrayList<>();
        appHelperMock = mock(AppHelper.class);
        fileRepositoryMock = mock(FileRepository.class);

        // Настраиваем мок для репозитория
        when(appHelperMock.getRepository()).thenReturn(fileRepositoryMock);

        // Создаем экземпляр BookService с моками
        bookService = new BookService(books, appHelperMock);
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        when(appHelperMock.create()).thenReturn(book);

        boolean result = bookService.add();

        assertTrue(result, "Добавление книги должно вернуть true");
        assertEquals(1, books.size(), "Книга должна быть добавлена в список");
        verify(fileRepositoryMock).save(books); // Проверяем, что книга сохраняется в репозитории
    }

    @Test
    void testAddBookWithNull() {
        when(appHelperMock.create()).thenReturn(null);

        boolean result = bookService.add();

        assertFalse(result, "Добавление null книги должно вернуть false");
        assertTrue(books.isEmpty(), "Список книг должен остаться пустым");
        verify(fileRepositoryMock, never()).save(books); // Проверяем, что save не вызывается
    }

    @Test
    void testPrintBooks() {
        // Создаем книгу для добавления в список
        Book book = new Book();
        book.setTitle("Test Book");
        books.add(book);

        // Настраиваем мок репозитория для возврата списка с книгой
        when(appHelperMock.getRepository()).thenReturn(fileRepositoryMock);
        when(fileRepositoryMock.load()).thenReturn(books);

        // Запускаем метод print(), который должен вызвать printList с книгами
        bookService.print();

        // Проверяем, что printList был вызван с ожидаемым списком книг
        verify(appHelperMock).printList(books);
    }

    @Test
    void testListBooks() {
        List<Book> loadedBooks = new ArrayList<>();
        loadedBooks.add(new Book());
        when(fileRepositoryMock.load()).thenReturn(loadedBooks);

        List<Book> result = bookService.list();

        assertEquals(loadedBooks, result, "Список книг должен совпадать с загруженным из репозитория");
        assertEquals(1, result.size(), "Загруженный список должен содержать одну книгу");
    }

    @Test
    void testEditBook() {
        Book book = new Book();
        assertFalse(bookService.edit(book), "Метод edit пока не реализован, должен возвращать false");
    }

    @Test
    void testRemoveBook() {
        Book book = new Book();
        assertFalse(bookService.remove(book), "Метод remove пока не реализован, должен возвращать false");
    }
}
