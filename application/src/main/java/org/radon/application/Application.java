package org.radon.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
		"com.radon"
})
@EnableJpaRepositories(basePackages = "com.radon.infrastructure")
@EnableScheduling
@EntityScan(basePackages = "com.radon.infrastructure")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
