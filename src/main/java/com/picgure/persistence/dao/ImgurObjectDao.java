package com.picgure.persistence.dao;

import com.picgure.persistence.dto.ImgurObjectDTO;
import org.hibernate.HibernateException;

import java.util.List;

/*
 * @author Jeet Prakash
 * */
public interface ImgurObjectDao {

	public Long save(ImgurObjectDTO dto) throws HibernateException;
	
	public List<ImgurObjectDTO> findByObjecthash(String objectHash) throws HibernateException;;
	
	public List<ImgurObjectDTO> findBySubreddit(String subreddit) throws HibernateException;;

	public List<ImgurObjectDTO> search(String title, String subreddit) throws HibernateException;;

}
