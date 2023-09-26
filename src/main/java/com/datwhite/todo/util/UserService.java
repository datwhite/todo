package com.datwhite.todo.util;

import com.datwhite.todo.entity.User;
import com.datwhite.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Создать пользователя
    public boolean registerUser(User user) {
        User newUser = userRepository.findByUsername(user.getUsername());
        if (newUser != null) {
            return false;
        }

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
        return true;
    }

    //Найти пользователя по имени пользователя и паролю
    public long findUser(User user) {
        User dbUser = userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());

        if (dbUser == null)
            return -1;

        return dbUser.getUserid();
    }
}
