package com.picgure.api.manager.impl;

import com.google.common.collect.Lists;
import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.util.Setting;
import com.picgure.persistence.dao.PicgureSettingRepository;
import com.picgure.persistence.dto.PicgureSettingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SettingServiceImpl implements SettingsService {

    private static Logger logger = Logger.getLogger(SettingServiceImpl.class.getName());

    @Autowired
    PicgureSettingRepository repository;

    @Autowired
    FileService fileService;

    @Override
    public void saveDefaultSettings() {
        List<PicgureSettingDTO> storedSetting = Lists.newArrayList(repository.findAll());
        List<PicgureSettingDTO> newDefaultSettings = Lists.newArrayList();

        for (PicgureSettingDTO defaultSettingDTO : getDefaultSettings()) {
            boolean have = false;
            for (PicgureSettingDTO storedSettingDTO : storedSetting) {
                if (storedSettingDTO.getName().equals(defaultSettingDTO.getName())) {
                    have = true;
                }
            }

            if (!have) {
                newDefaultSettings.add(defaultSettingDTO);
            }
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
        Map<String, String> settings =
                StreamSupport.stream(repository.findAll().spliterator(), false)
                     .collect(Collectors.toMap(dto -> dto.getName(), dto -> dto.getValue()));
        logger.info("Get settings " + settings);
        return settings;
    }

    @Override
    public void printSettings() {
        logger.info("Printing the settings");
        this.getSettings().forEach((key, val) -> System.out.println(key.toString() + " " + val.toString()));
    }

    @Override
    public void saveSetting(Map<String, String> settings) {
        logger.info("Saving the settings " + settings);
        settings.forEach((key, val) -> repository.save(new PicgureSettingDTO(key, val)));
    }

    @Override
    public void updateSetting(String name, String value) {
        if (name.equalsIgnoreCase(Setting.ImageStore.toString())
                && (!new File(value).isAbsolute())) {
            System.out.println("Directory path must be absolute");
            return;
        }
        logger.info("Updating the setting");
        PicgureSettingDTO dto = repository.findByName(name);
        if (dto != null) {
            dto.setValue(value);
            repository.save(dto);
            logger.info("Updated the setting " + dto.toString());
        } else {
            System.out.println("Invalid setting name");
        }
    }

    private List<PicgureSettingDTO> getDefaultSettings() {
        return Lists.newArrayList(new PicgureSettingDTO(Setting.ImageStore.toString(),
                                        fileService.defaultImageStoreDirectory().getAbsolutePath()));
    }

}