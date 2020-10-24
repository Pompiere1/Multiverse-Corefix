package me.francesco.multiversecorefix.utils;

import it.mycraft.powerlib.config.ConfigManager;
import me.francesco.multiversecorefix.MultiverseCoreFix;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {

    private ConfigManager configManager;
    private MultiverseCoreFix main;

    public FileManager(MultiverseCoreFix main){
        this.main = main;
        this.configManager = new ConfigManager(this.main);
        createConfigs();
        this.reloadFiles();
    }

    public void createConfigs(){
        this.configManager.create("config.yml");
        this.configManager.create("lang.yml");
    }

    public FileConfiguration getConfig() {
        return configManager.get("config.yml");
    }

    public FileConfiguration getLang() {
        return configManager.get("lang.yml");
    }

    public void saveConfig(String config, FileConfiguration fc) {
        configManager.save(config, fc);
    }

    public void reloadConfig() {
        configManager.reload("config.yml");
    }

    public void reloadLang() {
        configManager.reload("lang.yml");
    }

    public void reloadFiles() {
        System.out.println("Reload");
        reloadConfig();
        reloadLang();
        configManager.reloadAll();
    }
}
