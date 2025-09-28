package org.example;

import java.util.List;

public interface UserDAO {
    User getUserById(Long id) throws Exception;
    List<User> getAllUsers() throws Exception;
    void saveUser(User user) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(Long id) throws Exception;
}
