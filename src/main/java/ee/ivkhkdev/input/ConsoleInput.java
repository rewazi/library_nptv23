package ee.ivkhkdev.input;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String getString() {
        return scanner.nextLine();
    }
}
