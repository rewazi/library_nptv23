package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.User;

import java.util.List;

public class UserService implements Service<User> {
    private final List<User> users;
    private final AppHelper<User> appHelperUser;

    public UserService(List<User> users, AppHelper<User> appHelperUser) {
        this.users = users;
        this.appHelperUser = appHelperUser;
    }

    @Override
    public boolean add() {
        try {
            User user = appHelperUser.create(); // Создаем пользователя через AppHelper
            if (user == null) {
                return false;
            }
            users.add(user); // Добавляем пользователя в список
            appHelperUser.getRepository().save(users); // Сохраняем список в репозиторий
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean edit(User entity) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(entity.getId())) {
                users.set(i, entity); // Обновляем пользователя
                appHelperUser.getRepository().save(users); // Сохраняем обновленный список
                return true;
            }
        }
        System.out.println("User not found for editing.");
        return false;
    }

    @Override
    public boolean remove(User entity) {
        boolean removed = users.removeIf(user -> user.getId().equals(entity.getId())); // Удаляем пользователя по ID
        if (removed) {
            appHelperUser.getRepository().save(users); // Сохраняем изменения в репозитории
        }
        return removed;
    }

    @Override
    public void print() {
        appHelperUser.printList(this.list()); // Печатаем список пользователей через AppHelper
    }

    @Override
    public List<User> list() {
        return users; // Возвращаем текущий список пользователей
    }
}
