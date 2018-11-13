package com.picgure.api.manager;

import java.util.Map;

/*
 * @author Jeet Prakash
 * */

public interface SettingsService {

    public void saveDefaultSettings();

    public Map<String, String> getSettings();

    public void printSettings();

    public void saveSetting(Map<String, String> settings);

    public void updateSetting(String name, String value);

}
