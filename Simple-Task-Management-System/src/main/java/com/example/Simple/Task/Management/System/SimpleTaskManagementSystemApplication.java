package com.example.Simple.Task.Management.System;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.Simple.Task.Management.System.mapper") // <-- Add this line
public class SimpleTaskManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleTaskManagementSystemApplication.class, args);
	}
}