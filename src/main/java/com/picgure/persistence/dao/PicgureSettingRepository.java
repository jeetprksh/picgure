package com.picgure.persistence.dao;

import com.picgure.persistence.dto.ImgurObjectDTO;
import com.picgure.persistence.dto.PicgureSettingDTO;
import org.springframework.data.repository.CrudRepository;

public interface PicgureSettingRepository extends CrudRepository<PicgureSettingDTO, Long> {
}
