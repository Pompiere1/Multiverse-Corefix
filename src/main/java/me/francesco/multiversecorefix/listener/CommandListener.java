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
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String command = e.getMessage().replaceFirst("/", "");
        if(command.startsWith("mv") || command.startsWith("multiverse")){
            for(String s : plugin.getConfig().getStringList("mv-disabled-characters")){
                if(command.contains(s)){
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&' , plugin.getConfig().getString("Messages.not-allowed")));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onCommandBlockCommand(ServerCommandEvent e){
        CommandSender cs = e.getSender();
        if(cs instanceof BlockCommandSender){
            if(e.getCommand().startsWith("mv") || e.getCommand().startsWith("multiverse")){
                for(String s : plugin.getConfig().getStringList("mv-disabled-characters")){
                    if(e.getCommand().contains(s)){
                        e.setCancelled(true);
                        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&' , plugin.getConfig().getString("Messages.not-allowed")));
                        return;
                    }
                }
            }
        }
    }
}