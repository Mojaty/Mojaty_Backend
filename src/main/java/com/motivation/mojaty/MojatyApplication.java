package com.motivation.mojaty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MojatyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MojatyApplication.class, args);
	}

}
