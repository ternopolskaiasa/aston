package org.example;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Testcontainers
class UserDAOTest {

    @Container
    private static final PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("Andrew K")
            .withPassword("password");

    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() {
        System.setProperty("hibernate.connection.url", db.getJdbcUrl());
        System.setProperty("hibernate.connection.username", db.getUsername());
        System.setProperty("hibernate.connection.password", db.getPassword());

        userDAO = new UserDAOImpl();
    }

    @Test
    void testGetUserById() throws Exception {
        User testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setAge(25);
        testUser.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(testUser);

        User retrievedUser = userDAO.getUserById(testUser.getId());

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(testUser.getId());
        assertThat(retrievedUser.getName()).isEqualTo(testUser.getName());
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        user1.setAge(18);
        user1.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user1);

        User user2 = new User();
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setAge(20);
        user2.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user2);

        List<User> allUsers = userDAO.getAllUsers();

        assertThat(allUsers).hasSize(2);
        assertThat(allUsers).extracting("name")
                .containsExactlyInAnyOrder("User 1", "User 2");
    }

    @Test
    void testUpdateUser() throws Exception {
        User userToUpdate = new User();
        userToUpdate.setName("Old Name");
        userToUpdate.setEmail("old@example.com");
        userToUpdate.setAge(25);
        userToUpdate.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(userToUpdate);

        userToUpdate.setName("New Name");
        userToUpdate.setEmail("new@example.com");
        userToUpdate.setAge(30);

        userDAO.updateUser(userToUpdate);

        User updatedUser = userDAO.getUserById(userToUpdate.getId());
        assertThat(updatedUser.getName()).isEqualTo("New Name");
        assertThat(updatedUser.getEmail()).isEqualTo("new@example.com");
        assertThat(updatedUser.getAge()).isEqualTo(30);
    }

    @Test
    void testDeleteUser() throws Exception {
        User userToDelete = new User();
        userToDelete.setName("User 1");
        userToDelete.setEmail("user1@example.com");
        userToDelete.setAge(18);
        userToDelete.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(userToDelete);

        Long userId = userToDelete.getId();

        assertThat(userDAO.getUserById(userId)).isNotNull();

        userDAO.deleteUser(userId);

        assertThat(userDAO.getUserById(userId)).isNull();

        List<User> allUsers = userDAO.getAllUsers();
        assertThat(allUsers).noneMatch(user -> user.getId().equals(userId));
    }
}