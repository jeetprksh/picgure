package com.picgure.persistence.dao;

import com.picgure.persistence.dto.PicgureSettingDTO;

import java.util.List;

public interface PicgureSettingRepository {

    public Integer save(PicgureSettingDTO dto);

    public PicgureSettingDTO findByName(String name);

    public List<PicgureSettingDTO> findAll();

}
