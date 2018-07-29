package com.picgure.persistence.dao;

import java.util.List;

import com.picgure.persistence.dto.ImgurObjectDTO;

public interface ImgurObjectsDAO {
	
	public Long addImgurObject(ImgurObjectDTO imgurObjectDTO);
	
	public List<ImgurObjectDTO> searchImgurByTitle(String searchString);
	
	public List<ImgurObjectDTO> getImgurObjectByHash(String hash);
	
	public List<ImgurObjectDTO> getImgurObjectsBySubreddit(String subreddit);
	
}
