package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.interfaces.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppHelperBookTest {
    @Mock
    private Input input;

    @Mock
    private Service<Author> authorService;

    @Mock
    private FileRepository<Book> bookRepository;

    @InjectMocks
    private AppHelperBook appHelperBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBookWithAuthors() {
        when(input.getString()).thenReturn("My Book", "n", "1", "1", "2023");

        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setAuthorName("John");
        author.setAuthorSurname("Doe");
        authors.add(author);

        when(authorService.list()).thenReturn(authors);

        Book book = appHelperBook.create();

        assertNotNull(book);
        assertEquals("My Book", book.getTitle());
        assertEquals(1, book.getAuthors().size());
        assertEquals(author.getAuthorName(), book.getAuthors().get(0).getAuthorName());
        assertEquals(2023, book.getPublishedYear());

        verify(input, times(5)).getString();
        verify(authorService, times(1)).list();
    }

    @Test
    void testCreateBookWithNewAuthor() {
        when(input.getString()).thenReturn("My Book", "y");

        Book book = appHelperBook.create();

        assertNull(book);
        verify(input, times(2)).getString();
    }

    @Test
    void testPrintList() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setTitle("My Book");

        Author author = new Author();
        author.setAuthorName("John");
        author.setAuthorSurname("Doe");
        book.getAuthors().add(author);
        book.setPublishedYear(2023);
        books.add(book);

        appHelperBook.printList(books);

        verify(input, never()).getString();
    }

}