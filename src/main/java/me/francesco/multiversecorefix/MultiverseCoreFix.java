package me.francesco.multiversecorefix;

import me.francesco.multiversecorefix.command.MultiverseCoreFixCommand;
import me.francesco.multiversecorefix.listener.CommandListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiverseCoreFix extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("mvcfix").setExecutor(new MultiverseCoreFixCommand(this));
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        if(isMVCEnabled())
            getLogger().info(ChatColor.translateAlternateColorCodes('&',"&b&l[&e&lMultiverse-CoreFix&b&l]&a enable without errors"));
        else
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&b&l[&e&lMultiverse-CoreFix&b&l]&4MultiverseCore not found"));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&b&l[&e&lMultiverse-CoreFix&be&l]&c disabled without errors"));
    }

    public boolean isMVCEnabled(){
        try{
            Class.forName("com.onarandombox.MultiverseCore.MultiverseCore");
            return true;
        }catch (ClassNotFoundException e){
            return false;
        }
    }

}
