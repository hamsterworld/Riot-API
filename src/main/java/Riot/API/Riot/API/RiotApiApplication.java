package Riot.API.Riot.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityListeners;

@SpringBootApplication
@EnableJpaAuditing
public class RiotApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotApiApplication.class, args);
	}

}
