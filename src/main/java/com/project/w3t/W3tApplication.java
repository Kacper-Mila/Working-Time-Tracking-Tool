package com.project.w3t;

import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class W3tApplication {

	public static void main(String[] args) {
		SpringApplication.run(W3tApplication.class, args);
	}
}
