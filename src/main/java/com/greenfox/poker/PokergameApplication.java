package com.greenfox.poker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class PokergameApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokergameApplication.class, args);

		System.out.println("HelloWorld");
	}
}
