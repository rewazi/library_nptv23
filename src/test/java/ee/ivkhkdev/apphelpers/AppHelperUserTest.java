package ee.ivkhkdev.apphelpers;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.model.User;
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

class AppHelperUserTest {

    private FileRepository<User> userRepositoryMock;
    private AppHelperUser appHelperUser;
    private final InputStream originalIn = System.in; // Сохраняем стандартный System.in
    private final PrintStream originalOut = System.out; // Сохраняем стандартный System.out

    @BeforeEach
    void setUp() {
        // Создаем mock для FileRepository и экземпляр AppHelperUser
        userRepositoryMock = mock(FileRepository.class);
    }

    @AfterEach
    void tearDown() {
        // Сбрасываем System.in и System.out после теста
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testCreateUser() {
        // Подготавливаем ввод через ByteArrayInputStream
        String input = "Иван\nПетров\n1234567890\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());

        // Создаем AppHelperUser с mock-репозиторием и симулированным вводом
        appHelperUser = new AppHelperUser(userRepositoryMock, in);

        // Вызываем метод create и проверяем результат
        User user = appHelperUser.create();

        // Проверка, что user создан с ожидаемыми значениями
        assertEquals("Иван", user.getFirstname(), "Имя пользователя должно быть 'Иван'");
        assertEquals("Петров", user.getLastname(), "Фамилия пользователя должна быть 'Петров'");
        assertEquals("1234567890", user.getPhone(), "Телефон пользователя должен быть '1234567890'");
    }

    @Test
    void testPrintList() {
        // Подготавливаем поток для захвата вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Перенаправляем System.out

        // Создаем список пользователей для теста
        List<User> users = Arrays.asList(
                new User("Иван", "Петров", "1234567890"),
                new User("Алексей", "Сидоров", "0987654321")
        );


        appHelperUser = new AppHelperUser(userRepositoryMock, System.in);

        // Вызываем метод printList
        appHelperUser.printList(users);

        // Захватываем фактический вывод и удаляем лишние пробелы и переносы строк
        String actualOutput = outputStream.toString().trim();

        // Ожидаемый результат
        String expectedOutput = "---------- Список пользователей --------\n" +
                "1. Иван Петров, Телефон: 1234567890\n" +
                "2. Алексей Сидоров, Телефон: 0987654321";

        // Сравниваем строки построчно
        String[] expectedLines = expectedOutput.split("\\n");
        String[] actualLines = actualOutput.split("\\n");

        assertEquals(expectedLines.length, actualLines.length, "Количество строк в выводе не совпадает");

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i].trim(), actualLines[i].trim(), "Различие в строке " + (i + 1));
        }
    }

    @Test
    void testGetRepository() {
        // Создаем AppHelperUser
        appHelperUser = new AppHelperUser(userRepositoryMock, System.in);

        // Проверяем, что метод getRepository возвращает правильный объект
        assertEquals(userRepositoryMock, appHelperUser.getRepository(), "Метод getRepository() должен вернуть правильный FileRepository");
    }
}
