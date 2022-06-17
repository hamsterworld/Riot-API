package Riot.API.Riot.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "Riot.API.Riot.API")
public class RiotApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotApiApplication.class, args);
	}

}
