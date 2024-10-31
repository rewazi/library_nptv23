package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.model.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AppHelperAuthorTest {

    private FileRepository<Author> authorRepositoryMock;
    private AppHelperAuthor appHelperAuthor;
    private final InputStream originalIn = System.in; // Сохраняем стандартный System.in
    private final PrintStream originalOut = System.out; // Сохраняем стандартный System.out

    @BeforeEach
    void setUp() {
        // Создаем mock для FileRepository и экземпляр AppHelperAuthor с ByteArrayInputStream
        authorRepositoryMock = mock(FileRepository.class);
    }

    @AfterEach
    void tearDown() {
        // Сбрасываем System.in и System.out после теста
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testCreateAuthor() {
        // Подготавливаем ввод через ByteArrayInputStream
        String input = "Иван\nПетров\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());

        // Создаем AppHelperAuthor с mock-репозиторием и симулированным вводом
        appHelperAuthor = new AppHelperAuthor(authorRepositoryMock, in);

        // Вызываем метод create и проверяем результат
        Author author = appHelperAuthor.create();

        // Проверка, что author создан с ожидаемыми значениями
        assertEquals("Иван", author.getAuthorName(), "Имя автора должно быть 'Иван'");
        assertEquals("Петров", author.getAuthorSurname(), "Фамилия автора должна быть 'Петров'");
    }

    @Test
    void testPrintList() {
        // Подготавливаем поток для захвата вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Перенаправляем System.out

        // Создаем список авторов для теста
        List<Author> authors = Arrays.asList(
                new Author("Иван", "Петров"),
                new Author("Алексей", "Сидоров")
        );


        appHelperAuthor = new AppHelperAuthor(authorRepositoryMock, System.in);

        // Вызываем метод printList
        appHelperAuthor.printList(authors);

        // Захватываем фактический вывод и удаляем лишние пробелы и переносы строк
        String actualOutput = outputStream.toString().trim();

        // Ожидаемый результат
        String expectedOutput = "---------- Список авторов --------\n" +
                "1. Иван Петров\n" +
                "2. Алексей Сидоров";

        // Сравниваем строки построчно
        String[] expectedLines = expectedOutput.split("\\n");
        String[] actualLines = actualOutput.split("\\n");

        assertEquals(expectedLines.length, actualLines.length, "Количество строк в выводе не совпадает");

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i].trim(), actualLines[i].trim(), "Различие в строке " + (i + 1));
        }
    }
}
