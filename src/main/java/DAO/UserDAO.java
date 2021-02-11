package DAO;

import entity.User;

import java.util.List;

public interface UserDAO {
    User get(Integer id);
    void update(User user);
    void delete(Integer id);
    void add(User user);

    List<User> getAll();

}
