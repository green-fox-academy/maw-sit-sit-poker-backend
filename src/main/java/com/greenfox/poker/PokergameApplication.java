package com.greenfox.poker;

import com.greenfox.poker.repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class PokergameApplication implements CommandLineRunner{

	@Autowired
	GameRepo gameRepo;

	public static void main(String[] args) {
		SpringApplication.run(PokergameApplication.class, args);

		System.out.println("HelloWorld");
	}

	@Override
	public void run(String... args) throws Exception {
	}


}
