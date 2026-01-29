package com.internship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.internship.model")
public class Project5Application {

	public static void main(String[] args) {
		SpringApplication.run(Project5Application.class, args);
	}

}
