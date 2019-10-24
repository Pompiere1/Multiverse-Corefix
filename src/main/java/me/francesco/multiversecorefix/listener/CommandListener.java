package me.francesco.multiversecorefix.listener;

import me.francesco.multiversecorefix.MultiverseCoreFix;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    private MultiverseCoreFix plugin;

    public CommandListener(MultiverseCoreFix p){
        this.plugin = p;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        String command = event.getMessage().replaceFirst("/", "");

        if (!command.startsWith("mv") || !command.startsWith("multiverse")) return;

        for (String string : plugin.getConfig().getStringList("mv-disabled-characters")){
            if (command.contains(string)){
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&' , plugin.getConfig().getString("Messages.not-allowed")));
                return;
            }
        }

    }

    @EventHandler
    public void onCommandBlockCommand(ServerCommandEvent event){
        CommandSender commandSender = event.getSender();
        if (!(commandSender instanceof BlockCommandSender)) return;

        if(!event.getCommand().startsWith("mv") || !event.getCommand().startsWith("multiverse")) return;

        for(String string : plugin.getConfig().getStringList("mv-disabled-characters")){
            if(event.getCommand().contains(string)){
                event.setCancelled(true);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&' , plugin.getConfig().getString("Messages.not-allowed")));
                return;
            }


        }
    }
}