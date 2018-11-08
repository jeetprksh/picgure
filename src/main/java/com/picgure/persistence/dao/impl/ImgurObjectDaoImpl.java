package com.picgure.persistence.dao.impl;

import com.picgure.persistence.dao.ImgurObjectDao;
import com.picgure.persistence.dto.ImgurObjectDTO;

import java.util.List;

/*
 * @author Jeet Prakash
 * */

public class ImgurObjectDaoImpl extends CommonDao implements ImgurObjectDao {

    @Override
    public Integer save(ImgurObjectDTO dto) {
        return null;
    }

    @Override
    public List<ImgurObjectDTO> findByObjecthash(String objectHash) {
        return null;
    }

    @Override
    public List<ImgurObjectDTO> findBySubreddit(String subreddit) {
        return null;
    }

    @Override
    public List<ImgurObjectDTO> search(String title, String subreddit) {
        // "SELECT i FROM ImgurObjectDTO i WHERE i.title LIKE %:title% or i.subreddit LIKE %:subreddit%"
        return null;
    }
}
