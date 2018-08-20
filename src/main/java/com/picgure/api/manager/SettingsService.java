package com.picgure.api.manager;

import com.picgure.persistence.dto.PicgureSettingDTO;

import java.util.Map;

public interface SettingsService {

    public void saveDefaultSettings();

    public Map<String, String> getSettings();

    public void printSettings();

    public void saveSetting(Map<String, String> setting);

}
