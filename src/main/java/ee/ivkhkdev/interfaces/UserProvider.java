package ee.ivkhkdev.interfaces;

import ee.ivkhkdev.model.User;

public interface UserProvider {
    User create(Input input);
    String getList();
}
