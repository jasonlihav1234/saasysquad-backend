package com.saasysquad.backend_tings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendTingsApplication {

	public static void main(String[] args) {
		System.out.println("RAW ENV VAR: [" + System.getenv("DATABASE_URL") + "]");
		SpringApplication.run(BackendTingsApplication.class, args);
	}

}
