package com.picgure.api.manager.impl;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.util.Setting;
import com.picgure.logging.PicgureLogger;
import com.picgure.persistence.dao.SettingsDao;
import com.picgure.persistence.dao.impl.SettingsDaoImpl;
import com.picgure.persistence.dto.PicgureSettingDTO;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 * @author Jeet Prakash
 * */
public class SettingsServiceImpl implements SettingsService {

    private static Logger logger = PicgureLogger.getLogger(SettingsServiceImpl.class);

    private final SettingsDao repository;
    private final FileService fileService;

    public SettingsServiceImpl() {
        this.repository = new SettingsDaoImpl();
        this.fileService = new FileServiceImpl();
    }

    /*
    * Method to check whether there are new additional settings that needs to
    * be added into the database. Method getDefaultSettings() is being used
    * have all the setting which might contain new once those settings are then
    * compared to the one stored on database.
    * */
    @Override
    public void saveDefaultSettings() throws RuntimeException {

        List<PicgureSettingDTO> storedSettings = repository.findAll();
        List<PicgureSettingDTO> newDefaultSettings = Collections.emptyList();

        for (PicgureSettingDTO defaultSettingDTO : getDefaultSettings()) {
            boolean have = false;
            for (PicgureSettingDTO storedSettingDTO : storedSettings)
                if (storedSettingDTO.getName().equals(defaultSettingDTO.getName())) {
                    have = true;
                    break;
                }

            if (!have) newDefaultSettings.add(defaultSettingDTO);
        }

        if (!newDefaultSettings.isEmpty()) {
            logger.info("Saving new default setting " + newDefaultSettings);
            Map<String, String> map = newDefaultSettings.stream()
                    .collect(Collectors.toMap(dto -> dto.getName(), dto -> dto.getValue()));
            saveSetting(map);
        }
    }

    @Override
    public Map<String, String> getSettings() {
        Map<String, String> settings = repository.findAll().stream()
                .collect(Collectors.toMap(dto -> dto.getName(), dto -> dto.getValue()));
        logger.info("Fetched settings " + settings);
        return settings;
    }

    @Override
    public void saveSetting(Map<String, String> settings) throws RuntimeException {
        logger.info("Saving settings " + settings);
        settings.forEach((key, val) -> repository.save(new PicgureSettingDTO(key, val)));
    }

    @Override
    public void updateSetting(String name, String value) throws RuntimeException {
        if (name.equalsIgnoreCase(Setting.ImageStore.toString()) && (!new File(value).isAbsolute())) {
            throw new RuntimeException("Directory path must be absolute");
        }

        PicgureSettingDTO dto = repository.findByName(name);
        if (!Objects.isNull(dto)) {
            dto.setValue(value);
            repository.save(dto);
            logger.info("Updated the setting " + dto.toString());
        } else {
            logger.warning("Invalid setting name for update " + name);
        }
    }

    private List<PicgureSettingDTO> getDefaultSettings() {
        return Arrays.asList(new PicgureSettingDTO(Setting.ImageStore.toString(),
                                fileService.defaultImageStoreDirectory().getAbsolutePath()));
    }

}