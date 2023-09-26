package com.datwhite.todo.controller;

import com.datwhite.todo.entity.ToDo;
import com.datwhite.todo.entity.User;
import com.datwhite.todo.util.ToDoService;
import com.datwhite.todo.util.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    private static Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ToDoService toDoService;

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        log.info("register user " + user.toString());
        return userService.registerUser(user);
    }

    @PostMapping("/auth")
    public long authorization(@RequestBody User user) {
        log.info("find user " + user.getUsername());
        return userService.findUser(user);
    }

    @GetMapping("/todo/{id}/all")
    public List<ToDo> getAllTasks(@PathVariable long id) {
        log.info("getAllTasks, userId " + id);
        return toDoService.allTasks(id);
    }

    @GetMapping("/todo/{id}/today")
    public List<ToDo> getTasksForToday(@PathVariable long id) {
        log.info("getTasksForToday, userId " + id);
        return toDoService.todayTasks(id);
    }

    @GetMapping("/todo/{id}/week")
    public ResponseEntity<List<ToDo>> getTasksForWeek(@PathVariable long id) {
        log.info("getTasksForWeek, userId " + id);
        return new ResponseEntity<>(toDoService.weekTasks(id), HttpStatus.OK);
    }

    @GetMapping("/todo/{id}/expired")
    public List<ToDo> getExpiresTasks(@PathVariable long id) {
        log.info("getExpiresTasks, userId " + id);
        return toDoService.expiresTasks(id);
    }

    @PostMapping("/create")
    public void createTask(@RequestBody ToDo todo) {
        log.info("createTask, todo " + todo.toString());
        toDoService.createTask(todo);
    }

    @PostMapping("/edit")
    public ResponseEntity<ToDo> editTask(@RequestBody ToDo todo) {
        log.info("editTask, todo " + todo.toString());
        return ResponseEntity.ok(toDoService.editToDo(todo));
    }

    @PutMapping("/todo/{id}/done")
    public ResponseEntity doneTask(@PathVariable long id) {
        log.info("doneTask, id " + id);
        return ResponseEntity.ok(toDoService.done(id));
    }

    @PutMapping("/todo/{id}/undone")
    public ResponseEntity undoneTask(@PathVariable long id) {
        log.info("undoneTask, id " + id);
        return ResponseEntity.ok(toDoService.undone(id));
    }

    @DeleteMapping("/todo/{id}/delete")
    public ResponseEntity deleteTask(@PathVariable long id) {
        log.info("deleteTask, id " + id);
        return ResponseEntity.ok(toDoService.delete(id));
    }
}
