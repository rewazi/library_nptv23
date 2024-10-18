package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.impl.AppHelperBooks;
import ee.ivkhkdev.interfaces.impl.ConsoleInput;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.services.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppTest {

    private ConsoleInput inputMock;
    private AppHelperBooks bookProviderMock;
    private Book bookMock;

    @BeforeEach
    void setUp() {
        this.inputMock = Mockito.mock(ConsoleInput.class);
        this.bookProviderMock = Mockito.mock(AppHelperBooks.class);
        this.bookMock = Mockito.mock(Book.class);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void AppTest(){
        when(bookProviderMock.create(inputMock)).thenReturn(bookMock);
        BookService bookService = new BookService(bookProviderMock);
        boolean result = bookService.add(inputMock);
        verify(bookProviderMock).create(inputMock);
        assertTrue(result);
    }
}