package com.motivation.mojaty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class MojatyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MojatyApplication.class, args);
	}

}
