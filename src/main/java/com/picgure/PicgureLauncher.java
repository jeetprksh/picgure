package com.picgure;

import com.picgure.ui.root.RootFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;
import java.util.logging.Logger;

@Configuration
@SpringBootApplication
@ComponentScan("com.picgure")
@EnableJpaRepositories("com.picgure.persistence")
@EntityScan("com.picgure.persistence")
public class PicgureLauncher implements CommandLineRunner {

	private static Logger logger = Logger.getLogger(PicgureLauncher.class.getName());

	@Autowired private RootFrame rootFrame;
	
	public static void main(String[] args) {
		logger.info("Starting the Application.");
		new SpringApplicationBuilder(PicgureLauncher.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		java.awt.EventQueue.invokeLater(() -> {
			try {
				rootFrame.setVisible(true);
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
				logger.severe("Unable to initialize UI");
			}
		});
	}

}
