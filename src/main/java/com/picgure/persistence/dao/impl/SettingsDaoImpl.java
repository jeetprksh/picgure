package com.picgure.persistence.dao.impl;

import com.picgure.persistence.dao.SettingsDao;
import com.picgure.persistence.dto.PicgureSettingDTO;

import java.util.List;

/*
 * @author Jeet Prakash
 * */

public class SettingsDaoImpl extends CommonDao implements SettingsDao {

    @Override
    public Integer save(PicgureSettingDTO dto) {
        return null;
    }

    @Override
    public PicgureSettingDTO findByName(String name) {
        return null;
    }

    @Override
    public List<PicgureSettingDTO> findAll() {
        return listAll(PicgureSettingDTO.class);
    }
}
