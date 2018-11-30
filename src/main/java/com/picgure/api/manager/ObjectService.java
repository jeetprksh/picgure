package com.picgure.api.manager;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

import java.util.List;

/*
 * @author Jeet Prakash
 * */
public interface ObjectService {
	
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void poolDownloadObjects(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void downloadImgurObjectByHash(String hash) throws Exception;
	
	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery) throws Exception;

	public List<ImgurObjectAttrs> searchLocalRepoByTitleAndReddit(String title, String reddit);
}
