package com.jeetprksh.imgur.downloader;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jeetprksh.imgur.downloader.api.manager.ObjectDownloader;
import com.jeetprksh.imgur.downloader.entity.ImgurObjectAttrs;
import com.jeetprksh.imgur.downloader.entity.ImgurSearchQuery;

@SpringBootApplication
public class ApplicationLauncher implements CommandLineRunner {
	
	Logger logger = Logger.getLogger(ApplicationLauncher.class.getName());
	
	@Autowired
	ObjectDownloader manager;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationLauncher.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery();
		
		imgurSearchQuery.setBase("r");
		imgurSearchQuery.setRedditName("right");
		imgurSearchQuery.setSortOrder("new");
		
		List<ImgurObjectAttrs> allImgurObjectAttrs = manager.listAllObjectInSubreddit(imgurSearchQuery);
		
		this.logger.info("List size :: " + allImgurObjectAttrs.size());
		
		// Calculate overall size
		long size = 0;
		for (ImgurObjectAttrs imgurObject : allImgurObjectAttrs) {
			size += imgurObject.getSize();
		}
		this.logger.info("Overall Size :: " + size);
		
		manager.poolDownloadAllImgurObjectsInSubreddit(allImgurObjectAttrs);
		
	}
}
