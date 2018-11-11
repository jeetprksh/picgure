package com.picgure.persistence.dao.impl;

import com.picgure.persistence.dao.ImgurObjectDao;
import com.picgure.persistence.dto.ImgurObjectDTO;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author Jeet Prakash
 * */
public class ImgurObjectDaoImpl extends CommonDao implements ImgurObjectDao {

    @Override
    public Long save(ImgurObjectDTO dto) throws HibernateException {
        return saveEntity(dto);
    }

    @Override
    public List<ImgurObjectDTO> findByObjecthash(String objectHash) throws HibernateException {
        String namedQuery = "imgurobjectstab.findByObjecthash";
        Map<String, String> param = new HashMap<>();
        param.put("objectHash", objectHash);
        return list(namedQuery, ImgurObjectDTO.class, param);
    }

    @Override
    public List<ImgurObjectDTO> findBySubreddit(String subreddit) throws HibernateException {
        String namedQuery = "imgurobjectstab.findBySubreddit";
        Map<String, String> param = new HashMap<>();
        param.put("subreddit", subreddit);
        return list(namedQuery, ImgurObjectDTO.class, param);
    }

    @Override
    public List<ImgurObjectDTO> search(String title, String subreddit) throws HibernateException {
        String namedQuery = "imgurobjectstab.search";
        Map<String, String> param = new HashMap<>();
        param.put("title", title);
        param.put("subreddit", subreddit);
        return list(namedQuery, ImgurObjectDTO.class, param);
    }
}
