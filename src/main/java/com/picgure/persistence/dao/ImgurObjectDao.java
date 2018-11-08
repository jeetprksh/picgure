package com.picgure.persistence.dao;

import com.picgure.persistence.dto.ImgurObjectDTO;

import java.util.List;

/*
 * @author Jeet Prakash
 * */

public interface ImgurObjectDao {

	public Integer save(ImgurObjectDTO dto);
	
	public List<ImgurObjectDTO> findByObjecthash(String objectHash);
	
	public List<ImgurObjectDTO> findBySubreddit(String subreddit);

	public List<ImgurObjectDTO> search(String title, String subreddit);

}
