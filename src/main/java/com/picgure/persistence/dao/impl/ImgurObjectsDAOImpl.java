package com.picgure.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.picgure.persistence.DataAccess;
import com.picgure.persistence.dao.ImgurObjectsDAO;
import com.picgure.persistence.dto.ImgurObjectDTO;

@Repository
public class ImgurObjectsDAOImpl extends DataAccess implements ImgurObjectsDAO {

	@Override
	@Transactional(rollbackFor=Exception.class, readOnly=false)
	public Long addImgurObject(ImgurObjectDTO imgurObjectDTO) {
		return (Long)this.saveObject(imgurObjectDTO);
	}

	@Override
	@Transactional(rollbackFor=Exception.class, readOnly=true)
	public List<ImgurObjectDTO> searchImgurByTitle(String searchString) {
		String namedQuery = "imgurObject.searchObjectsByTitle";
		return getObjectList(namedQuery, ImgurObjectDTO.class,
				"searchstring", "%"+searchString.toUpperCase()+"%");
	}

	@Override
	@Transactional(rollbackFor=Exception.class, readOnly=true)
	public List<ImgurObjectDTO> getImgurObjectByHash(String hash) {
		String namedQuery = "imgurObject.getImgurObjectByHash";
		return getObjectList(namedQuery, ImgurObjectDTO.class, "hash", hash);
	}

	@Override
	@Transactional(rollbackFor=Exception.class, readOnly=true)
	public List<ImgurObjectDTO> getImgurObjectsBySubreddit(String subreddit) {
		String namedQuery = "imgurObject.getImgurObjectBySubreddit";
		return getObjectList(namedQuery, ImgurObjectDTO.class, "subreddit", subreddit);
	}

}
