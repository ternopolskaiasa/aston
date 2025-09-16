package org.example;

import java.util.List;

public interface UserDAO {
    User getUserById(Long id) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    void saveUser(User user) throws DAOException;
    void updateUser(User user) throws DAOException;
    void deleteUser(Long id) throws DAOException;
}
