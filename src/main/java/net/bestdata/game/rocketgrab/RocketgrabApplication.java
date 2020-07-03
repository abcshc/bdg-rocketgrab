package net.bestdata.game.rocketgrab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
public class RocketgrabApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketgrabApplication.class, args);
	}

}
