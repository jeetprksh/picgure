package com.picgure.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.picgure.persistence.dto.ImgurObjectDTO;
import org.springframework.data.repository.query.Param;

public interface ImgurObjectRepository extends CrudRepository<ImgurObjectDTO, Long> {
	
	public List<ImgurObjectDTO> findByObjecthash(String objectHash);
	
	public List<ImgurObjectDTO> findBySubreddit(String subreddit);

	@Query("SELECT i FROM ImgurObjectDTO i WHERE i.title LIKE %:title% or i.subreddit LIKE %:subreddit%")
	public List<ImgurObjectDTO> searchByTitle(@Param("title") String title, @Param("subreddit") String subreddit);

}
