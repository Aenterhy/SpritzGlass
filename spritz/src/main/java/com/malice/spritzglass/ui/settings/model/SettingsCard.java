package com.malice.spritzglass.ui.settings.model;

/**
 * @author Aenterhy.
 */
public class SettingsCard {
    private SettingType type;
    private String title;
    private int icon;
    private String current;

    public SettingsCard(SettingType type, String title, int icon, String current) {
        this.title = title;
        this.type = type;
        this.icon = icon;
        this.current = current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public SettingType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public String getCurrent() {
        return current;
    }
}
