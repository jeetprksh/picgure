package com.picgure.persistence.dao.impl;

import com.picgure.persistence.dao.SettingsDao;
import com.picgure.persistence.dto.PicgureSettingDTO;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author Jeet Prakash
 * */
public class SettingsDaoImpl extends CommonDao implements SettingsDao {

    @Override
    public Integer save(PicgureSettingDTO dto) throws HibernateException {
        return saveEntity(dto).intValue();
    }

    @Override
    public PicgureSettingDTO findByName(String name) throws HibernateException {
        String namedQuery = "picguresetting.findByName";
        Map<String, String> param = new HashMap<>();
        param.put("name", name);
        return getSingle(namedQuery, PicgureSettingDTO.class, param);
    }

    @Override
    public List<PicgureSettingDTO> findAll() throws HibernateException {
        String namedQuery = "picguresetting.findAll";
        return list(namedQuery, PicgureSettingDTO.class, new HashMap<>());
    }
}
