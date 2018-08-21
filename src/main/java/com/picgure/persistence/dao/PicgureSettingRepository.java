package com.picgure.persistence.dao;

import com.picgure.persistence.dto.ImgurObjectDTO;
import com.picgure.persistence.dto.PicgureSettingDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PicgureSettingRepository extends CrudRepository<PicgureSettingDTO, Long> {

    public PicgureSettingDTO findByName(String name);

}
