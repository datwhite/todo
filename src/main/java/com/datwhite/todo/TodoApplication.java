package com.datwhite.todo;

import com.datwhite.todo.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplication {

	private static Logger log = LoggerFactory.getLogger(MainController.class);

	public static void main(String[] args) {
		log.info("My Log: ToDoApplication Started");
		SpringApplication.run(TodoApplication.class, args);
	}

}
