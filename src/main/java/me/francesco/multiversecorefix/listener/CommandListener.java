package me.francesco.multiversecorefix.listener;

import it.mycraft.powerlib.chat.Message;
import me.francesco.multiversecorefix.MultiverseCoreFix;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    private MultiverseCoreFix plugin;
    private FileConfiguration config;
    private FileConfiguration lang;
    private String prefix;

    public CommandListener() {
        this.plugin = MultiverseCoreFix.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.config = plugin.getConfig();
        this.lang = plugin.getLang();
        this.prefix = new Message(lang.getString("Messages.prefix")).getText();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        System.out.println("#1");
        String command = e.getMessage().replaceFirst("/", "");
        if (!command.startsWith("mv") && !command.startsWith("multiverse")) {
            return;
        }
        System.out.println("#2");
        Player player = e.getPlayer();
        for (String s : config.getStringList("mv-disabled-characters")) {
            if (command.contains(s)) {
                e.setCancelled(true);
                new Message(lang.getString("Messages.not-allowed"))
                        .addPlaceHolder("{prefix}", prefix)
                        .send(player);
                alert();
                punish(e.getPlayer());
                return;
            }
        }
    }


    @EventHandler
    public void onCommandBlockCommand(ServerCommandEvent e) {
        String command = e.getCommand().replaceFirst("/", "");
        if (!command.startsWith("mv") && !command.startsWith("multiverse")) {
            return;
        }

        if (e.getSender() instanceof ConsoleCommandSender) {
            System.out.println("E' LA CONSOLE");
            return;
        }
        for (String s : config.getStringList("mv-disabled-characters")) {
            if (command.contains(s)) {
                e.setCancelled(true);
                new Message(lang.getString("Messages.not-allowed"))
                        .addPlaceHolder("{prefix}", prefix)
                        .send(e.getSender());
                alert();
                punish(e.getSender());
                return;
            }
        }
    }

    public void alert() {
        if (config.getBoolean("alert-on-command.alert-in-game")) {
            if (config.getBoolean("alert-on-command.permission")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("test")) {
                        new Message(lang.getString("Messages.alert")).send(p);
                    }
                }
            }
            if (config.getBoolean("alert-on-command.op")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        new Message(lang.getString("Messages.alert")).send(p);
                    }
                }
            }
        }
    }

    public void punish(CommandSender sender) {
        String reason = new Message(lang.getString("Messages.kick-ban-reason")).getText();
        Player player = (Player) sender;
        if (config.getBoolean("punishment.ban")) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, null, null);
        }
        if (config.getBoolean("punishment.kick")) {
            if (!player.isOnline())
                return;
            player.kickPlayer(reason);
        }
    }
}
