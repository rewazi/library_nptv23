package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.User;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class AppHelperUser implements AppHelper<User>, Input {
    private final FileRepository<User> userRepository;
    private final Scanner scanner;

    // Конструктор с InputStream для Scanner
    public AppHelperUser(FileRepository<User> userRepository, InputStream inputStream) {
        this.userRepository = userRepository;
        this.scanner = new Scanner(inputStream);
    }

    public FileRepository<User> getRepository() {
        return userRepository;
    }

    @Override
    public User create() {
        try {
            User user = new User();
            System.out.print("Имя пользователя: ");
            user.setFirstname(getString());
            System.out.print("Фамилия пользователя: ");
            user.setLastname(getString());
            System.out.print("Телефон пользователя: ");
            user.setPhone(getString());
            return user;
        } catch (Exception e) {
            System.out.println("Ошибка при создании пользователя: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void printList(List<User> users) {
        System.out.println("---------- Список пользователей --------");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.printf("%d. %s %s, Телефон: %s%n", i + 1, user.getFirstname(), user.getLastname(), user.getPhone());
        }
    }

    @Override
    public String getString() {
        return scanner.nextLine();
    }
}
