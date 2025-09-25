package com.GetOnClouds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class GetOnCloudsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetOnCloudsApplication.class, args);
		System.out.println("Started...");
	}

}
