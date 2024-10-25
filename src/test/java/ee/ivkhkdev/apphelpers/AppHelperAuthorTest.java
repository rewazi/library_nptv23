package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.model.Author;
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

class AppHelperAuthorTest {
    Input inputMock;
    PrintStream outDefault;
    ByteArrayOutputStream outMock;
    AppHelperAuthor appHelperAuthor;

    @BeforeEach
    void setUp() {
        inputMock = Mockito.mock(Input.class);
        outDefault = System.out;
        outMock = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outMock));
        appHelperAuthor = new AppHelperAuthor(inputMock);
    }

    @AfterEach
    void tearDown() {
        inputMock = null;
        System.setOut(outDefault);
        outMock= null;
    }

    @Test
    void create() {
        when(inputMock.getString()).thenReturn("Lev","Tolstoy");
        Author actual = appHelperAuthor.create();
        Author expected = new Author("Lev","Tolstoy");
        assertEquals(expected.getAuthorName(), actual.getAuthorName());
        assertEquals(expected.getAuthorSurname(), actual.getAuthorSurname());
    }

    @Test
    void printList() {
        Author author = new Author("Lev","Tolstoy");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        appHelperAuthor.printList(authors);
        String actual = outMock.toString();
        String expected = "1. Lev Tolstoy";
        assertTrue(actual.contains(expected));

    }
}