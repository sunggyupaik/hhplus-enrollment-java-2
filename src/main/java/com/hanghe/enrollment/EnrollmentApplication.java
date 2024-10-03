package com.hanghe.enrollment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EnrollmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnrollmentApplication.class, args);
	}
}
