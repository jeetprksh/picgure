package com.picgure.command;

import java.util.List;
import java.util.logging.Logger;

import com.picgure.api.manager.SettingsService;
import com.picgure.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.picgure.api.manager.ObjectService;
import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

@ShellComponent
public class ApplicationCommands {
	
	private static Logger logger = Logger.getLogger(ApplicationCommands.class.getName());
	
	@Autowired
	ObjectService objectService;

	@Autowired
	SettingsService settingsService;

	@ShellMethod("Download images.")
    public void download(@ShellOption String redditName,
                         @ShellOption(defaultValue=Constants.SORT_ORDER_NEW) String order) {

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

    @ShellMethod("Probe a particular reddit for information.")
    public void probe(@ShellOption String redditName) {
		ImgurSearchQuery imgurSearchQuery = new ImgurSearchQuery(redditName, Constants.SORT_ORDER_NEW);

		List<ImgurObjectAttrs> allImgurObjectAttrs = objectService.getObjectsInSubreddit(imgurSearchQuery);

		// Calculate overall size
		long size = 0;
		for (ImgurObjectAttrs imgurObject : allImgurObjectAttrs) {
			size += imgurObject.getSize();
		}
		logger.info("There are " + allImgurObjectAttrs.size() + " objects with overall size of " + size + " on " + redditName);
	}

	@ShellMethod("Analyse local storage for whether a particular reddit is already downloaded")
	public void analysis(@ShellOption String reddit,
						 @ShellOption String title) {
		logger.info(reddit + " " + title);
		List<ImgurObjectAttrs> attrs = objectService.searchObjectsByTitle(title, reddit);
		for (ImgurObjectAttrs attr : attrs) {
			logger.info(attr.toString());
		}
	}

	@ShellMethod("View and update application settings")
	public void settings(@ShellOption(defaultValue = Constants.BLANK_STRING) String setting,
						 @ShellOption(defaultValue = Constants.BLANK_STRING) String value) {
		if (!setting.equals(Constants.BLANK_STRING)) {
			settingsService.updateSetting(setting, value);
		} else {
			settingsService.printSettings();
		}
	}
	
}
