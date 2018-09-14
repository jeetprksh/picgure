package com.picgure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Logger;

@Configuration
@SpringBootApplication
@ComponentScan("com.picgure")
@EnableJpaRepositories("com.picgure.persistence")
@EntityScan("com.picgure.persistence")
public class PicgureLauncher {

	private static Logger logger = Logger.getLogger(PicgureLauncher.class.getName());
	
	public static void main(String[] args) {
		logger.info("Starting the Application.");
		SpringApplication.run(PicgureLauncher.class, args);
	}

}
