package com.picgure.api.manager;

import java.util.Map;

/*
 * @author Jeet Prakash
 * */
public interface SettingsService {

    public void saveDefaultSettings() throws RuntimeException;

    public Map<String, String> getSettings();

    public void saveSetting(Map<String, String> settings) throws RuntimeException;

    public void updateSetting(String name, String value) throws RuntimeException;

}
