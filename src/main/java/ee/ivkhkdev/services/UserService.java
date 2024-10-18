package ee.ivkhkdev.services;

import ee.ivkhkdev.App;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.UserProvider;
import ee.ivkhkdev.model.User;

public class UserService {
    private UserProvider userProvider;

    public UserService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public boolean add(Input input) {
        try {
            User user = userProvider.create(input);
            if (user == null) return false;

            for (int i = 0; i < App.users.length; i++) {
                if (App.users[i] == null) {
                    App.users[i] = user;
                    return true; // Exit once user is added
                }
            }
            // Optionally handle the case when the user array is full
            System.out.println("User list is full.");
            return false;
        } catch (Exception e) {
            // Log the exception (you can use a logger)
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public String printListUsers() {
        return userProvider.getList();
    }
}