package ee.ivkhkdev.interfaces.impl;

import ee.ivkhkdev.interfaces.Input;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String getString() {
        return scanner.nextLine();
    }
}
