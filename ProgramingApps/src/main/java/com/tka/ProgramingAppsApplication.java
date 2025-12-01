package com.tka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class ProgramingAppsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramingAppsApplication.class, args);
		System.err.println("<-- Welcome to Programing Test Application------------->\n\n");
	}

}
