package com.datwhite.todo;

import com.datwhite.todo.entity.ToDo;
import com.datwhite.todo.entity.User;
import com.datwhite.todo.repository.ToDoRepository;
import com.datwhite.todo.util.ToDoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTests {
    @Mock
    private ToDoRepository toDoRepository;

    private ToDoService toDoService;

    @BeforeEach
    public void setup() {
        toDoService = new ToDoService(toDoRepository);
    }

    @Test
    public void createTask() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now(), 1, false);

        Mockito.doReturn(toDo).when(toDoRepository).save(toDo);
        Assertions.assertEquals(toDo, toDoService.createTask(toDo));
    }

    @Test
    public void editToDo() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now(), 1, false);

        Mockito.doReturn(toDo).when(toDoRepository).save(toDo);
        Assertions.assertEquals(toDo, toDoService.createTask(toDo));
    }

    @Test
    public void allTasks() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo1 = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo2 = new ToDo(user.getUserid(), "Task #2", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo3 = new ToDo(user.getUserid(), "Task #3", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo4 = new ToDo(user.getUserid(), "Task #4", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo5 = new ToDo(user.getUserid(), "Task #5", "Task comment", LocalDateTime.now(), 1, false);
        List<ToDo> allTasks = new ArrayList<>();
        allTasks.add(toDo1);
        allTasks.add(toDo2);
        allTasks.add(toDo3);
        allTasks.add(toDo4);
        allTasks.add(toDo5);

        Mockito.doReturn(allTasks).when(toDoRepository).findAllByUseridAndDoneFalse(user.getUserid());
        Assertions.assertEquals(allTasks, toDoService.allTasks(user.getUserid()));
    }

    @Test
    public void todayTasks() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo1 = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now().minusDays(2), 1, false);
        ToDo toDo2 = new ToDo(user.getUserid(), "Task #2", "Task comment", LocalDateTime.now().minusDays(1), 1, false);
        ToDo toDo3 = new ToDo(user.getUserid(), "Task #3", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo4 = new ToDo(user.getUserid(), "Task #4", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo5 = new ToDo(user.getUserid(), "Task #5", "Task comment", LocalDateTime.now().plusDays(1), 1, false);
        List<ToDo> allTasks = new ArrayList<>();
        allTasks.add(toDo1);
        allTasks.add(toDo2);
        allTasks.add(toDo3);
        allTasks.add(toDo4);
        allTasks.add(toDo5);

        List<ToDo> todayTasksList = new ArrayList<>();
        todayTasksList.add(toDo3);
        todayTasksList.add(toDo4);

        LocalDate today = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(1);

        Mockito.doAnswer(todayTasks ->
                allTasks.stream().filter(task ->
                        task.getTododate().isAfter(start) && task.getTododate().isBefore(end)
                ).collect(Collectors.toList())
        ).when(toDoRepository).findAllByUseridAndTododateRange(user.getUserid(), start, end);

        Assertions.assertEquals(todayTasksList, toDoRepository.findAllByUseridAndTododateRange(user.getUserid(), start, end));
        Assertions.assertEquals(todayTasksList, toDoService.todayTasks(user.getUserid()));
    }

    @Test
    public void weekTasks() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo1 = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now().minusDays(1), 1, false);
        ToDo toDo2 = new ToDo(user.getUserid(), "Task #2", "Task comment", LocalDateTime.now(), 1, false);
        ToDo toDo3 = new ToDo(user.getUserid(), "Task #3", "Task comment", LocalDateTime.now().plusDays(3), 1, false);
        ToDo toDo4 = new ToDo(user.getUserid(), "Task #4", "Task comment", LocalDateTime.now().plusDays(6), 1, false);
        ToDo toDo5 = new ToDo(user.getUserid(), "Task #5", "Task comment", LocalDateTime.now().plusDays(10), 1, false);
        List<ToDo> allTasks = new ArrayList<>();
        allTasks.add(toDo1);
        allTasks.add(toDo2);
        allTasks.add(toDo3);
        allTasks.add(toDo4);
        allTasks.add(toDo5);

        List<ToDo> weekTasksList = new ArrayList<>();
        weekTasksList.add(toDo2);
        weekTasksList.add(toDo3);
        weekTasksList.add(toDo4);

        LocalDate today = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(7);

        Mockito.doAnswer(weekTasks ->
                allTasks.stream().filter(task ->
                        task.getTododate().isAfter(start) && task.getTododate().isBefore(end)
                ).collect(Collectors.toList())
        ).when(toDoRepository).findAllByUseridAndTododateRange(user.getUserid(), start, end);

        Assertions.assertEquals(weekTasksList, toDoRepository.findAllByUseridAndTododateRange(user.getUserid(), start, end));
        Assertions.assertEquals(weekTasksList, toDoService.weekTasks(user.getUserid()));
    }

    @Test
    public void expiresTasks() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo1 = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now().minusDays(2), 1, false);
        ToDo toDo2 = new ToDo(user.getUserid(), "Task #2", "Task comment", LocalDateTime.now().minusDays(1), 1, false);
        ToDo toDo3 = new ToDo(user.getUserid(), "Task #3", "Task comment", LocalDateTime.now().plusDays(3), 1, false);
        ToDo toDo4 = new ToDo(user.getUserid(), "Task #4", "Task comment", LocalDateTime.now().plusDays(6), 1, false);
        ToDo toDo5 = new ToDo(user.getUserid(), "Task #5", "Task comment", LocalDateTime.now().plusDays(10), 1, false);
        List<ToDo> allTasks = new ArrayList<>();
        allTasks.add(toDo1);
        allTasks.add(toDo2);

        List<ToDo> expiresTasksList = new ArrayList<>();
        expiresTasksList.add(toDo1);
        expiresTasksList.add(toDo2);

        Mockito.doAnswer(todayTasks ->
                allTasks.stream().filter(task ->
                        task.getTododate().isBefore(LocalDateTime.now())
                ).collect(Collectors.toList())
        ).when(toDoRepository).findAllByUseridExpired(user.getUserid());

        Assertions.assertEquals(expiresTasksList, toDoRepository.findAllByUseridExpired(user.getUserid()));
        Assertions.assertEquals(expiresTasksList, toDoService.expiresTasks(user.getUserid()));
    }

    @Test
    public void done() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now(), 1, false);

        Mockito.lenient().doReturn(Optional.of(toDo)).when(toDoRepository).findByTodoid(toDo.getTodoid());

        toDo = toDoRepository.findByTodoid(toDo.getTodoid()).get();
        toDo.setDone(true);

        Mockito.doReturn(toDo).when(toDoRepository).save(toDo);

        Assertions.assertTrue(toDo.isDone());
        Assertions.assertEquals(toDo, toDoRepository.save(toDo));
        Assertions.assertEquals(toDo, toDoService.done(toDo.getTodoid()));
    }

    @Test
    public void undone() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now(), 1, true);

        Mockito.lenient().doReturn(Optional.of(toDo)).when(toDoRepository).findByTodoid(toDo.getTodoid());

        toDo = toDoRepository.findByTodoid(toDo.getTodoid()).get();
        toDo.setDone(false);

        Mockito.doReturn(toDo).when(toDoRepository).save(toDo);

        Assertions.assertFalse(toDo.isDone());
        Assertions.assertEquals(toDo, toDoRepository.save(toDo));
        Assertions.assertEquals(toDo, toDoService.done(toDo.getTodoid()));
    }

    @Test
    public void delete() {
        User user = new User("User 1", "user1@mail.com", "password");
        ToDo toDo = new ToDo(user.getUserid(), "Task #1", "Task comment", LocalDateTime.now(), 1, true);

        Mockito.lenient().doReturn(Optional.of(toDo)).when(toDoRepository).findByTodoid(toDo.getTodoid());

        Assertions.assertEquals(toDo, toDoRepository.findByTodoid(toDo.getTodoid()).get());

        Assertions.assertTrue(toDoService.delete(toDo.getTodoid()));
    }
}
