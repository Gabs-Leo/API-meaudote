package com.gabsleo.meaudote;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeaudoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeaudoteApplication.class, args);
	}

	CommandLineRunner startup(){
		return args -> {

		};
	}
}
