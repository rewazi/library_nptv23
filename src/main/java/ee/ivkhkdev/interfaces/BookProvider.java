package ee.ivkhkdev.interfaces;

import ee.ivkhkdev.model.Book;

public interface BookProvider {
    Book create(Input input);
    String getList();
}
