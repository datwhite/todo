package com.datwhite.todo;

import com.datwhite.todo.entity.User;
import com.datwhite.todo.repository.UserRepository;
import com.datwhite.todo.util.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser() {
        User newUser1 = new User("New User 1", "user1@mail.com", "password");
        User newUser2 = new User("New User 2", "user2@mail.com", "password");

        //UserRepository возвращает null, потому что такого пользователя еще нет, создается новый пользователь
        Mockito.doReturn(null).when(userRepository).findByUsername(newUser1.getUsername());
        Assertions.assertTrue(userService.registerUser(newUser1));

        //UserRepository возвращает существующего пользователя из БД, новый пользователь не создается
        Mockito.doReturn(newUser2).when(userRepository).findByUsername(newUser2.getUsername());
        Assertions.assertFalse(userService.registerUser(newUser2));
    }

    @Test
    public void findUser() {
        User dbUser1 = new User("New User 1", "user1@mail.com", "password");
        User dbUser2 = new User("New User 2", "user2@mail.com", "password");

        //Пользователь найден, возвращается его id
        Mockito.doReturn(dbUser1).when(userRepository).findByUsernameAndPassword(dbUser1.getUsername(), dbUser1.getPassword());
        Assertions.assertEquals(dbUser1.getUserid(), userService.findUser(dbUser1));

        //Пользователь не найден, возвращается -1
        Mockito.doReturn(null).when(userRepository).findByUsernameAndPassword(dbUser2.getUsername(), dbUser2.getPassword());
        Assertions.assertEquals(-1, userService.findUser(dbUser2));
    }
}
