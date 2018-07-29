package com.picgure.api.manager;

import java.util.List;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

public interface ObjectDownloader {
	
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void poolDownloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void downloadImgurObjectByHash(String hash) throws Exception;
	
	public List<ImgurObjectAttrs> listAllObjectInSubreddit(ImgurSearchQuery imgurSearchQuery);
}
