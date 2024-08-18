package com.example.L15_Minor_Project_extention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class L15MinorProjectApplicationextention {

	public static void main(String[] args) {
		SpringApplication.run(L15MinorProjectApplicationextention.class, args);
		System.out.println("Hello");
	}

}
