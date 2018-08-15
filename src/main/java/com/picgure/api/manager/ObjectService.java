package com.picgure.api.manager;

import java.util.List;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

public interface ObjectService {
	
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void poolDownloadObjects(List<ImgurObjectAttrs> allImgurObjectAttrs);
	
	public void downloadImgurObjectByHash(String hash) throws Exception;
	
	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery);

	public List<ImgurObjectAttrs> searchObjectsByTitle(String searchParam, String subReddit);
}
