package me.francesco.multiversecorefix;

import lombok.Getter;
import me.francesco.multiversecorefix.command.MultiverseCoreFixCommand;
import me.francesco.multiversecorefix.listener.CommandListener;
import me.francesco.multiversecorefix.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static it.mycraft.powerlib.utils.ColorAPI.color;

public final class MultiverseCoreFix extends JavaPlugin {

    @Getter
    private static MultiverseCoreFix instance;

    @Getter
    private FileManager fileManager;

    private FileConfiguration config;

    @Getter
    private FileConfiguration lang;

    @Override
    public void onEnable() {
        instance = this;
        if((!isDependencyFound("PowerLib")) || !(isDependencyFound("Multiverse-Core"))) {
            return;
        }
        this.fileManager = new FileManager(this);
        config = fileManager.getConfig();
        lang = fileManager.getLang();

        new CommandListener();
        new MultiverseCoreFixCommand();
        //getServer().getPluginManager().registerEvents(new CommandListener(), this);
    }

    private boolean isDependencyFound(String plugin) {
        if(!Bukkit.getPluginManager().getPlugin(plugin).isEnabled()) {
            Bukkit.getConsoleSender().sendMessage(color("&6&n/!\\&r &4&n" + plugin + " &6 not found or disabled. Disabling Multiverse-CoreFix..."));
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    @Override
    public FileConfiguration getConfig(){
        return config;
    }
}
