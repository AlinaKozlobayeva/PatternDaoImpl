package ua.com.kozlobayeva.DAO;

import ua.com.kozlobayeva.Entity.User;


import java.util.List;

/**
  * Created by Alina on 24.03.2016.
 */
public interface UserDao extends GenericDao<String, User> {
    /**
     * Returns user with specific login.
     * @param login specific key for finding user with this key
     * @return User with login
     */
    User findUserByLogin(String login);

    @Override
    List<User> findAll();

    @Override
    boolean create(User user);

    @Override
    boolean update(User user);

    @Override
    boolean delete(String user);
}
