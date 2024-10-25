package ee.ivkhkdev.apphelpers;

import java.util.List;

public interface AppHelper<T> {
    T create();
    void printList(List<T> elements);
}
