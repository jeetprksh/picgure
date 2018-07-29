package com.picgure.commands;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.picgure.api.manager.ObjectDownloader;
import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

@ShellComponent
public class DownloadCommand {
	
	private static Logger logger = Logger.getLogger(DownloadCommand.class.getName());
	
	@Autowired
	ObjectDownloader manager;

	@ShellMethod("Download images.")
    public void download(@ShellOption(defaultValue="r") String base,
    		@ShellOption String redditName, @ShellOption(defaultValue="new") String order) {
		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery();
		
		imgurSearchQuery.setBase(base);
		imgurSearchQuery.setRedditName(redditName);
		imgurSearchQuery.setSortOrder(order);
		
		logger.info("OBJECT :: " + imgurSearchQuery);
		
		List<ImgurObjectAttrs> allImgurObjectAttrs = manager.listAllObjectInSubreddit(imgurSearchQuery);
		
		logger.info("List size :: " + allImgurObjectAttrs.size());
		
		// Calculate overall size
		long size = 0;
		for (ImgurObjectAttrs imgurObject : allImgurObjectAttrs) {
			size += imgurObject.getSize();
		}
		logger.info("Overall Size :: " + size);
		
		manager.poolDownloadAllImgurObjectsInSubreddit(allImgurObjectAttrs);
    }
	
}
