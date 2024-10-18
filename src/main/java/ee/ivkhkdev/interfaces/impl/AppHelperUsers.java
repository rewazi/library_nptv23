package ee.ivkhkdev.interfaces.impl;

import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.UserProvider;
import ee.ivkhkdev.model.User;

import java.util.ArrayList;
import java.util.List;

public class AppHelperUsers implements UserProvider {
    private final List<User> users = new ArrayList<>();

    @Override
    public User create(Input input) {
        try {
            User user = new User();
            System.out.print("Имя: ");
            user.setFirstname(input.getString());
            System.out.print("Фамилия: ");
            user.setLastname(input.getString());
            System.out.print("Телефон: ");
            user.setPhone(input.getString());
            users.add(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getList() {
        StringBuilder sbUsers = new StringBuilder();
        for (User user : users) {
            sbUsers.append(String.format("Имя: %s, Фамилия: %s, Телефон: %s%n",
                    user.getFirstname(), user.getLastname(), user.getPhone()));
        }
        return sbUsers.toString();
    }
}