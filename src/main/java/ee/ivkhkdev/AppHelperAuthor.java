package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Author;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class AppHelperAuthor implements AppHelper<Author>, Input {
    private final FileRepository<Author> authorRepository;
    private final Scanner scanner;

    // Новый конструктор с InputStream для scanner
    public AppHelperAuthor(FileRepository<Author> authorRepository, InputStream inputStream) {
        this.authorRepository = authorRepository;
        this.scanner = new Scanner(inputStream); // Инициализация scanner с inputStream
    }

    public FileRepository<Author> getRepository() {
        return authorRepository;
    }

    @Override
    public Author create() {
        try {
            Author author = new Author();
            System.out.print("Имя автора: ");
            author.setAuthorName(getString());
            System.out.print("Фамилия автора: ");
            author.setAuthorSurname(getString());
            return author;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void printList(List<Author> authors) {
        System.out.println("---------- Список авторов --------");
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            System.out.printf("%d. %s %s%n", i + 1, author.getAuthorName(), author.getAuthorSurname());
        }
    }

    @Override
    public String getString() {
        return scanner.nextLine();
    }
}
