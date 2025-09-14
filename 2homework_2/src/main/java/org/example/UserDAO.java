package org.example;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User getUserById(Long id) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void saveUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(Long id) throws SQLException;
}
