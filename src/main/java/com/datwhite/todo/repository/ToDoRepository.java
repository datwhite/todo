package com.datwhite.todo.repository;

import com.datwhite.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    Optional<ToDo> findByTodoid(long id);

    List<ToDo> findAllByUseridAndDoneFalse(long id);

    @Query(value = "select * from todo t where userid = ?1 and tododate between ?2 and ?3 and done is false", nativeQuery = true)
    List<ToDo> findAllByUseridAndTododateRange(long id, LocalDateTime startdate, LocalDateTime enddate);

    @Query(value = "select * from todo where userid = ?1 and tododate <= now() and done is false", nativeQuery = true)
    List<ToDo> findAllByUseridExpired(long id);

}
