package com.picgure.api.manager.impl;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.util.Setting;
import com.picgure.persistence.dao.PicgureSettingRepository;
import com.picgure.persistence.dao.impl.PicgureSettingRepoImpl;
import com.picgure.persistence.dto.PicgureSettingDTO;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SettingServiceImpl implements SettingsService {

    private static Logger logger = Logger.getLogger(SettingServiceImpl.class.getName());

    private final PicgureSettingRepository repository;
    private final FileService fileService;

    public SettingServiceImpl() {
        this.repository = new PicgureSettingRepoImpl();
        this.fileService = new FileServiceImpl();
    }

    /*
    * Method to check whether there are new additional settings that needs to
    * be added into the database. Method getDefaultSettings() is being used
    * have all the setting which might contain new once those settings are then
    * compared to the one stored on database.
    * */
    @Override
    public void saveDefaultSettings() {
        List<PicgureSettingDTO> storedSettings = StreamSupport.stream(repository.findAll().spliterator(), false)
                                                                .collect(Collectors.toList());
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
        } else System.out.println("Invalid setting name");
    }

    private List<PicgureSettingDTO> getDefaultSettings() {
        return Arrays.asList(new PicgureSettingDTO(Setting.ImageStore.toString(),
                                fileService.defaultImageStoreDirectory().getAbsolutePath()));
    }

}