package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    private List<Author> authors;

    @Mock
    private AppHelper<Author> appHelperAuthor;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        // Инициализируем мок-объекты и AuthorService
        MockitoAnnotations.openMocks(this);
        authors = new ArrayList<>();
        authorService = new AuthorService(authors, appHelperAuthor);
    }

    @Test
    void testAdd() {
        Author author = new Author(); // создаем тестовый объект Author
        when(appHelperAuthor.create()).thenReturn(author); // настраиваем AppHelper для возврата нового автора

        boolean result = authorService.add();

        assertTrue(result); // Проверяем, что метод вернул true
        assertEquals(1, authors.size()); // Проверяем, что автор добавлен в список
        assertEquals(author, authors.get(0)); // Проверяем, что добавлен именно наш объект author
        verify(appHelperAuthor, times(1)).create(); // Проверяем, что create был вызван один раз
    }

    @Test
    void testAddFailsWhenAppHelperReturnsNull() {
        when(appHelperAuthor.create()).thenReturn(null); // Настраиваем AppHelper для возврата null

        boolean result = authorService.add();

        assertFalse(result); // Проверяем, что метод вернул false
        assertEquals(0, authors.size()); // Проверяем, что список остается пустым
        verify(appHelperAuthor, times(1)).create(); // Проверяем, что create был вызван один раз
    }

    @Test
    void testEdit() {
        Author existingAuthor = new Author();
        authors.add(existingAuthor); // Добавляем автора в список для редактирования

        boolean result = authorService.edit(existingAuthor);

        assertFalse(result); // Метод edit еще не реализован, ожидаем false
    }

    @Test
    void testRemove() {
        Author author = new Author();
        authors.add(author); // Добавляем автора для тестирования удаления

        boolean result = authorService.remove(author);

        assertFalse(result); // Метод remove еще не реализован, ожидаем false
    }

    @Test
    void testPrint() {
        authorService.print(); // Печать списка авторов

        verify(appHelperAuthor, times(1)).printList(authors); // Проверяем, что printList был вызван один раз
    }

    @Test
    void testList() {
        List<Author> list = authorService.list();

        assertEquals(authors, list); // Проверяем, что метод list возвращает корректный список
    }
}
