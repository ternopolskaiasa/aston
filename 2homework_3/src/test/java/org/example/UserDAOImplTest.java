package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;

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

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static UserDAOImpl userDAO;
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setUp() {
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.connection.url", db.getJdbcUrl())
                .applySetting("hibernate.connection.username", db.getUsername())
                .applySetting("hibernate.connection.password", db.getPassword())
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .build();

        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("Hibernate initialization failed", e);
        }

        userDAO = new UserDAOImpl();
    }

    @BeforeEach
    void setUpTestData() {
        jdbcTemplate = new JdbcTemplate(db.getDataSource());
        jdbcTemplate.execute("TRUNCATE TABLE users CASCADE;");
        jdbcTemplate.execute("INSERT INTO users (id, name, email, age, created_at) " +
                "VALUES (1, 'User 1', 'test1@example.com', 25, NOW());");
        jdbcTemplate.execute("INSERT INTO users (id, name, email, age, created_at) " +
                "VALUES (2, 'User 2', 'test2@example.com', 30, NOW());");
    }

    @AfterAll
    static void tearDown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void cleanDatabase() throws DAOException {
        List<User> allUsers = userDAO.getAllUsers();
        for (User user : allUsers) {
            userDAO.deleteUser(user.getId());
        }
    }

    @Test
    void testGetUserById() throws DAOException {
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
    void testGetAllUsers() throws DAOException {
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
    void testUpdateUser() throws DAOException {
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
    void testDeleteUser() throws DAOException {
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

    @Test
    void testGetUserByIdWithNonExistingId() {
        Long nonExistingId = 999999L;

        assertThatExceptionOfType(DAOException.class)
                .isThrownBy(() -> userDAO.getUserById(nonExistingId))
                .withMessageContaining("Invalid id!");
    }

    @Test
    void testUpdateNonExistingUser() throws DAOException {
        User nonExistingUser = new User();
        nonExistingUser.setId(999999L);
        nonExistingUser.setName("Test");

        assertThatExceptionOfType(DAOException.class)
                .isThrownBy(() -> userDAO.updateUser(nonExistingUser))
                .withMessageContaining("User is not found!");
    }

    @Test
    void testDeleteNonExistingUser() {
        Long nonExistingId = 999999L;

        assertThatExceptionOfType(DAOException.class)
                .isThrownBy(() -> userDAO.deleteUser(nonExistingId))
                .withMessageContaining("User is not found!");
    }

    @Test
    void testSaveUserWithNullFields() {
        User invalidUser = new User();
        invalidUser.setName(null);
        invalidUser.setEmail(null);

        assertThatExceptionOfType(DAOException.class)
                .isThrownBy(() -> userDAO.saveUser(invalidUser))
                .withMessageContaining("Problems with adding new user!");
    }

    @Test
    void testUpdateUserWithInvalidEmail() throws DAOException {
        User validUser = new User();
        validUser.setName("Test");
        validUser.setEmail("test@example.com");
        validUser.setAge(25);
        validUser.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(validUser);

        validUser.setEmail("invalid-email");

        assertThatExceptionOfType(DAOException.class)
                .isThrownBy(() -> userDAO.updateUser(validUser))
                .withMessageContaining("Problems with updating user!");
    }
}
