package services;

import dao.UserDao;
import beansLab.entities.User;
import beansLab.entities.Shot;

import java.util.List;

public class UserService {

    private UserDao usersDao;

    public UserService() {
        usersDao = new UserDao();
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

    public Shot findShotById(int id) {
        return usersDao.findShotById(id);
    }

}