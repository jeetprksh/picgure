package com.jeetprksh.imgur.downloader.persistence.dao;

import java.util.List;

import com.jeetprksh.imgur.downloader.persistence.dto.ImgurObjectDTO;

public interface ImgurObjectsDAO {
	
	public Long addImgurObject(ImgurObjectDTO imgurObjectDTO);
	
	public List<ImgurObjectDTO> searchImgurByTitle(String searchString);
	
	public List<ImgurObjectDTO> getImgurObjectByHash(String hash);
	
	public List<ImgurObjectDTO> getImgurObjectsBySubreddit(String subreddit);
	
}
