package com.greenfox.poker;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.repository.GameRepo;
import com.greenfox.poker.service.GameService;
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
		gameRepo.save(new Game("Green", 10, 5));
		gameRepo.save(new Game("Blue", 20, 5));
		gameRepo.save(new Game("Red", 50, 5));

	}
}
