package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainTest {
    @Mock
    private Scanner scanner;

    @Mock
    private UserDAOImpl userDAO;

    @InjectMocks
    private Main main;

    @Test
    void testReadCommand() {
        when(scanner.nextLine()).thenReturn("3");
        assertEquals(3, main.readCommand());
    }

    @Test
    void testHandleGetUserById() throws Exception {
        long id = 123L;
        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setName("Test User");

        when(scanner.nextLine()).thenReturn(String.valueOf(id));
        when(userDAO.getUserById(id)).thenReturn(mockUser);

        main.handleGetUserById();

        verify(userDAO).getUserById(id);
    }

    @Test
    void testHandleGetAllUsers() throws Exception {
        List<User> mockUsers = List.of(new User());
        when(userDAO.getAllUsers()).thenReturn(mockUsers);

        main.handleGetAllUsers();

        verify(userDAO).getAllUsers();
    }

    @Test
    void testHandleSaveUser() throws Exception {
        when(scanner.nextLine()).thenReturn("John Doe")
                .thenReturn("john@example.com")
                .thenReturn("30");

        main.handleSaveUser();

        verify(userDAO).saveUser(
                argThat(user ->
                        "John Doe".equals(user.getName()) &&
                                "john@example.com".equals(user.getEmail()) &&
                                user.getAge().equals(30)
                )
        );
    }

    @Test
    void testHandleUpdateUser() throws Exception {
        long id = 123L;
        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setName("Old Name");

        when(scanner.nextLine()).thenReturn(String.valueOf(id))
                .thenReturn("name;New Name");
        when(userDAO.getUserById(id)).thenReturn(mockUser);

        main.handleUpdateUser();

        verify(userDAO).updateUser(
                argThat(user -> "New Name".equals(user.getName()))
        );
    }

    @Test
    void testHandleDeleteUser() throws Exception {
        long id = 456L;
        when(scanner.nextLine()).thenReturn(String.valueOf(id));

        main.handleDeleteUser();

        verify(userDAO).deleteUser(id);
    }

    @Test
    void testInvalidUpdateInput() throws Exception {
        when(scanner.nextLine()).thenReturn("123")
                .thenReturn("name");

        main.handleUpdateUser();

        verify(userDAO, never()).updateUser(any());
    }
}