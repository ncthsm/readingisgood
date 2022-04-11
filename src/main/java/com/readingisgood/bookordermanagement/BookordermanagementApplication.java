package com.readingisgood.bookordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BookordermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookordermanagementApplication.class, args);
	}

}
