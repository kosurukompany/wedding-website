package com.kosuru.kompany.satyawedschelsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:instance-based.properties")
public class SatyaWedsChelseaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SatyaWedsChelseaApplication.class, args);
	}

}
