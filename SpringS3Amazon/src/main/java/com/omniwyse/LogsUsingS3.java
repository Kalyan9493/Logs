package com.omniwyse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.omniwyse.services.S3Services;

@SpringBootApplication
public class LogsUsingS3 implements CommandLineRunner{

	
	@Autowired
	S3Services s3Services;
	
	
	public static void main(String[] args) {
		SpringApplication.run(LogsUsingS3.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Services.getFileslistFromFolder();
	}
}
