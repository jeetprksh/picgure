package com.picgure.api.manager;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

import java.util.List;

/*
 * @author Jeet Prakash
 * */
public interface ObjectService {

	public void poolDownloadObjects(List<ImgurObjectAttrs> allImgurObjectAttrs) throws Exception;

	public void downloadImgurObjectByHash(String hash) throws Exception;

	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery) throws Exception;

	public List<ImgurObjectAttrs> searchLocal(String title, String reddit);
}
