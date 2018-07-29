package com.jeetprksh.imgur.downloader;

import org.jline.utils.AttributedString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
@SpringBootApplication
public class ApplicationLauncher {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationLauncher.class, args);
	}
	
	@Bean
	public PromptProvider promptProvider() {
	    return () -> new AttributedString("picgure > ");
	}

}
