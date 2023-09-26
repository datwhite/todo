package com.datwhite.todo.util;

import com.datwhite.todo.entity.ToDo;
import com.datwhite.todo.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    //Создать задачу
    public ToDo createTask(ToDo todo) {
        return toDoRepository.save(todo);
    }

    //Редактировать задачу
    public ToDo editToDo(ToDo todo) {
        return toDoRepository.save(todo);
    }

    //Получить все невыполненные задачи пользователя
    public List<ToDo> allTasks(long id) {
        return toDoRepository.findAllByUseridAndDoneFalse(id);
    }

    //Получить все невыполненные задачи пользователя на текущий день
    public List<ToDo> todayTasks(long id) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(1);
        return toDoRepository.findAllByUseridAndTododateRange(id, start, end);
    }

    //Получить все невыполненные задачи пользователя на неделю
    public List<ToDo> weekTasks(long id) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(7);
        return toDoRepository.findAllByUseridAndTododateRange(id, start, end);
    }

    //Получить все просроченные задачи пользователя
    public List<ToDo> expiresTasks(long id) {
        return toDoRepository.findAllByUseridExpired(id);
    }

    //Изменить статус задачи на "Выполнено"
    public ToDo done(long id) {
        ToDo todo = toDoRepository.findByTodoid(id).get();
        todo.setDone(true);
        return toDoRepository.save(todo);
    }

    //Изменить статус задачи на "Не выполнено"
    public ToDo undone(long id) {
        ToDo todo = toDoRepository.findByTodoid(id).get();
        todo.setDone(false);
        return toDoRepository.save(todo);
    }

    //Удалить задачу
    public boolean delete(long id) {
        ToDo todo = toDoRepository.findByTodoid(id).get();
        toDoRepository.delete(todo);
        return true;
    }
}
