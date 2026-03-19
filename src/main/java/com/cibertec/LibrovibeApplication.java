package com.cibertec;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class LibrovibeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrovibeApplication.class, args);
		
		
		
		
	}
	@PostConstruct
	public void init() {
	    TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
	}
}
