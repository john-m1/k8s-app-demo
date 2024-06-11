package com.metro.atwater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// TDOD: exception thrown on exit by tomcat 
@SpringBootApplication
public class AtwaterApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AtwaterApp.class, args);
	}
}
