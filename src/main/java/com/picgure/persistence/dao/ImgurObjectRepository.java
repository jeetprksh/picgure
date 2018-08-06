package com.picgure.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.picgure.persistence.dto.ImgurObjectDTO;

public interface ImgurObjectRepository extends CrudRepository<ImgurObjectDTO, Long> {
	
	public List<ImgurObjectDTO> findByObjecthash(String objectHash);
	
	public List<ImgurObjectDTO> findBySubreddit(String subreddit);

}
