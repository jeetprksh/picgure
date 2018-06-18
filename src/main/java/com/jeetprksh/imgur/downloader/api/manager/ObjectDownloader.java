package com.jeetprksh.imgur.downloader.api.manager;

import java.util.List;

import com.jeetprksh.imgur.downloader.entity.ImgurObjectAttrs;
import com.jeetprksh.imgur.downloader.entity.ImgurSearchQuery;

public interface ObjectDownloader {
	
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void poolDownloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void downloadImgurObjectByHash(String hash) throws Exception;
	
	public List<ImgurObjectAttrs> listAllObjectInSubreddit(ImgurSearchQuery imgurSearchQuery);
}
