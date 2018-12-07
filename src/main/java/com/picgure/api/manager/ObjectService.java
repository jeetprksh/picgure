package com.picgure.api.manager;

import com.picgure.api.thread.DownloadProgress;
import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

import java.util.List;

/*
 * @author Jeet Prakash
 * */
public interface ObjectService {

	public DownloadProgress poolDownloadObjects(List<ImgurObjectAttrs> allImgurObjectAttrs) throws Exception;

	public void downloadImgurObjectByHash(String hash) throws Exception;

	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery) throws Exception;

	public List<ImgurObjectAttrs> searchLocal(String title, String reddit);
}
