package me.francesco.multiversecorefix.command;

import me.francesco.multiversecorefix.MultiverseCoreFix;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MultiverseCoreFixCommand implements CommandExecutor {

    private MultiverseCoreFix plugin;

    public MultiverseCoreFixCommand(MultiverseCoreFix plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mvcfix.admin")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.no-permission")));
            return true;
        }

        if (args.length > 0 && !args[0].equalsIgnoreCase("reload")) return false;

        plugin.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.reload-config")));

        return true;
    }
}
