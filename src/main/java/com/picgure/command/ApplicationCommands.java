package com.picgure.command;

import com.picgure.api.manager.ObjectService;
import com.picgure.api.manager.impl.ObjectServiceImpl;
import com.picgure.api.thread.DownloadProgress;
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
	private static final String SORT_ORDER_NEW = "new";
	private static final String SORT_ORDER_HOT = "hot";

	private ObjectService objectService;

	public ApplicationCommands() {
		this.objectService = new ObjectServiceImpl();
	}

    public DownloadProgress download(String redditName, String order) throws Exception {
		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery(redditName, order);
		logger.info("Search query " + imgurSearchQuery);

		List<ImgurObjectAttrs> imgurObjects = objectService.getObjectsInSubreddit(imgurSearchQuery);
		return objectService.poolDownloadObjects(imgurObjects);
    }

    public List<ImgurObjectAttrs> probe(String redditName) throws Exception {
		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery(redditName, SORT_ORDER_NEW);
		return objectService.getObjectsInSubreddit(imgurSearchQuery);
	}

	public List<ImgurObjectAttrs> analysis(String title, String reddit) {
		logger.info(reddit + " " + title);
		return objectService.searchLocal(title, reddit);
	}

}
