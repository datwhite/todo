package com.datwhite.todo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;
    private long userid;
    private String todoname;
    private String comment;
    private LocalDateTime tododate;
    private int todopriority;
    private boolean done;

    public ToDo() {}

    public ToDo(long userid, String todoname, String comment, LocalDateTime tododate, boolean done) {
        this.userid = userid;
        this.todoname = todoname;
        this.comment = comment;
        this.tododate = tododate;
        this.done = done;
    }

    public ToDo(long todoid, long userid, String todoname, String comment, LocalDateTime tododate, int todopriority, boolean done) {
        this.todoid = todoid;
        this.userid = userid;
        this.todoname = todoname;
        this.comment = comment;
        this.tododate = tododate;
        this.todopriority = todopriority;
        this.done = done;
    }

    public ToDo(long todoid, String todoname, String comment, LocalDateTime tododate, int todopriority, boolean done) {
        this.todoid = todoid;
        this.todoname = todoname;
        this.comment = comment;
        this.tododate = tododate;
        this.todopriority = todopriority;
        this.done = done;
    }

    public long getTodoid() {
        return todoid;
    }

    public void setTodoid(long todoid) {
        this.todoid = todoid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getTodoname() {
        return todoname;
    }

    public void setTodoname(String todoname) {
        this.todoname = todoname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTododate() {
        return tododate;
    }

    public void setTododate(LocalDateTime tododate) {
        this.tododate = tododate;
    }

    public int getTodopriority() {
        return todopriority;
    }

    public void setTodopriority(int todopriority) {
        this.todopriority = todopriority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "todoid=" + todoid +
                ", userid=" + userid +
                ", todoname='" + todoname + '\'' +
                ", comment='" + comment + '\'' +
                ", tododate=" + tododate +
                ", todopriority=" + todopriority +
                ", done=" + done +
                '}';
    }
}
