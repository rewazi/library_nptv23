package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.services.AuthorService;
import ee.ivkhkdev.services.BookService;
import ee.ivkhkdev.services.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AppHelperBookTest {
    Input inputMock;
    PrintStream outDefault;
    ByteArrayOutputStream outMock;
    Service<Author> authorServiceMock;
    AppHelper<Book> appHelperBook;
    @BeforeEach
    void setUp() {
        inputMock = Mockito.mock(Input.class);
        outDefault = System.out;
        outMock = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outMock));
        authorServiceMock = Mockito.mock(AuthorService.class);
        appHelperBook = new AppHelperBook(inputMock,authorServiceMock);
    }

    @AfterEach
    void tearDown() {
        inputMock = null;
        System.setOut(outDefault);
        outMock= null;
    }

    @Test
    void createWithAddAuthor() {
        when(inputMock.getString()).thenReturn("Voina i mir","y");
        Book actual = appHelperBook.create();
        assertTrue(actual == null);
    }
    @Test
    void createWithoutAddAuthor() {
        Author author = new Author("Lev","Tolstoy");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        when(authorServiceMock.list()).thenReturn(authors);
        when(inputMock.getString()).thenReturn("Voina i mir","n", "1", "1", "2000");
        Book actual = appHelperBook.create();
        Book expected = new Book("Voina i mir", authors,2000);
        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getPublishedYear(), expected.getPublishedYear());
        assertEquals(actual.getAuthors().get(0).getAuthorName(), expected.getAuthors().get(0).getAuthorName());
        assertEquals(actual.getAuthors().get(0).getAuthorSurname(), expected.getAuthors().get(0).getAuthorSurname());
    }

    @Test
    void printList() {
        Author author = new Author("Lev","Tolstoy");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        Book book = new Book("Voina i mir", authors,2000);
        List<Book> books = new ArrayList<>();
        books.add(book);
        appHelperBook.printList(books);
        String actual = outMock.toString();
//        System.setOut(outDefault);
//        System.out.println(actual);
        String expected = "1. Voina i mir. Lev Tolstoy. 2000";
        assertTrue(actual.contains(expected));

    }
}