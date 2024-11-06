package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Author;
import ee.ivkhkdev.model.Book;
import ee.ivkhkdev.interfaces.Service;

import java.util.List;

public class
AppHelperBook implements AppHelper<Book>, Input {
    private final Input input;
    private final Service<Author> authorService;
    private final FileRepository<Book> bookRepository;

    public AppHelperBook(Input input, Service<Author> authorService, FileRepository<Book> bookRepository) {
        this.input = input;
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    @Override
    public FileRepository<Book> getRepository() {
        return bookRepository;
    }

    @Override
    public Book create() {
        Book book = new Book();

        // Ввод названия книги
        System.out.print("Введите название книги: ");
        book.setTitle(getString());

        // Выбор авторов
        boolean addMoreAuthors = true;
        while (addMoreAuthors) {
            authorService.print();
            System.out.print("Выберите номер автора из списка: ");
            int authorIndex = Integer.parseInt(getString()) - 1;

            // Проверка корректности ввода
            if (authorIndex >= 0 && authorIndex < authorService.list().size()) {
                book.getAuthors().add(authorService.list().get(authorIndex));
                System.out.print("Хотите добавить еще одного автора? (y/n): ");
                String response = getString();
                addMoreAuthors = response.equalsIgnoreCase("y");
            } else {
                System.out.println("Некорректный номер автора. Повторите ввод.");
            }
        }

        // Ввод года издания
        System.out.print("Введите год издания книги: ");
        book.setPublishedYear(Integer.parseInt(getString()));

        return book;
    }

    @Override
    public void printList(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Список книг пуст.");
            return;
        }

        System.out.println("------ Список книг ------");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book != null) {
                StringBuilder authorsInfo = new StringBuilder();
                for (Author author : book.getAuthors()) {
                    authorsInfo.append(author.getAuthorName()).append(" ").append(author.getAuthorSurname()).append(", ");
                }
                if (authorsInfo.length() > 0) {
                    authorsInfo.setLength(authorsInfo.length() - 2); // Убираем лишнюю запятую и пробел
                }
                System.out.printf("%d. Название: %s, Авторы: %s, Год издания: %d%n",
                        i + 1,
                        book.getTitle(),
                        authorsInfo.toString(),
                        book.getPublishedYear()
                );
            }
        }
    }

    @Override
    public String getString() {
        return input.getString();
    }
}
