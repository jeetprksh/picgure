package com.picgure.persistence.dao;

import com.picgure.persistence.dto.PicgureSettingDTO;
import org.hibernate.HibernateException;

import java.util.List;

/*
 * @author Jeet Prakash
 * */
public interface SettingsDao {

    public Integer save(PicgureSettingDTO dto) throws HibernateException;

    public PicgureSettingDTO findByName(String name) throws HibernateException;

    public List<PicgureSettingDTO> findAll() throws HibernateException ;

}
