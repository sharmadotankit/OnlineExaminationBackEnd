package com.lti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.lti")
@EntityScan("com.lti")
public class OnlineExaminationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineExaminationAppApplication.class, args);
	}

}
