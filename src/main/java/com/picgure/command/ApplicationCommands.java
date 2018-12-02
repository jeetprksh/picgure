package com.picgure.command;

import com.picgure.api.manager.ObjectService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.manager.impl.ObjectServiceImpl;
import com.picgure.api.manager.impl.SettingsServiceImpl;
import com.picgure.api.util.Constants;
import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;
import com.picgure.logging.PicgureLogger;

import java.util.List;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class ApplicationCommands {
	
	private static Logger logger = PicgureLogger.getLogger(ApplicationCommands.class);

	private ObjectService objectService;
	private SettingsService settingsService;

	public ApplicationCommands() {
		this.objectService = new ObjectServiceImpl();
		this.settingsService = new SettingsServiceImpl();
	}

    public void download(String redditName, String order) throws Exception {

		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery(redditName, order);
		logger.info("OBJECT :: " + imgurSearchQuery);
		
		List<ImgurObjectAttrs> allImgurObjectAttrs = objectService.getObjectsInSubreddit(imgurSearchQuery);

		logger.info("List size :: " + allImgurObjectAttrs.size());

		// Calculate overall size
		long size = 0;
		for (ImgurObjectAttrs imgurObject : allImgurObjectAttrs) {
			size += imgurObject.getSize();
		}
		logger.info("Overall Size :: " + size);

		objectService.poolDownloadObjects(allImgurObjectAttrs);
    }

    public List<ImgurObjectAttrs> probe(String redditName) throws Exception {
		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery(redditName, Constants.SORT_ORDER_NEW);
		return objectService.getObjectsInSubreddit(imgurSearchQuery);
	}

	public List<ImgurObjectAttrs> analysis(String title, String reddit) {
		logger.info(reddit + " " + title);
		return objectService.searchLocalRepoByTitleAndReddit(title, reddit);
	}
	
}
