package me.francesco.multiversecorefix.command;

import it.mycraft.powerlib.chat.Message;
import me.francesco.multiversecorefix.MultiverseCoreFix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MultiverseCoreFixCommand implements CommandExecutor {

    private FileConfiguration config;
    private FileConfiguration lang;
    private MultiverseCoreFix plugin;

    public MultiverseCoreFixCommand() {
        this.plugin = MultiverseCoreFix.getInstance();
        this.config = plugin.getConfig();
        this.lang = plugin.getLang();
        plugin.getCommand("mvcfix").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = new Message(lang.getString("Messages.prefix")).getText();
        if (!sender.hasPermission("mvcfix.admin")) {
            new Message(lang
                    .getString("Messages.no-permission"))
                    .addPlaceHolder("{prefix}", prefix)
                    .send(sender);
            System.out.println("Ciao");
            return true;
        }
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    plugin.getFileManager().reloadFiles();
                    new Message(lang
                            .getString("Messages.reload-config"))
                            .addPlaceHolder("{prefix}", prefix)
                            .send(sender);
                    break;
                case "gdb":
                case "getdisabledcharacters":
                    new Message("&4&lDisabled Characters:").send(sender);
                    config.getStringList("mv-disabled-characters").
                            forEach((s) -> new Message("- \"" + s + "\"").send(sender));
                    break;
                case "help":
                    new Message(prefix + " &$&lUse &b/mvcfix <reload|gdb>").send(sender);
                default:
                    new Message(lang
                            .getString("Messages.sc-not-found"))
                            .addPlaceHolder("{prefix}", prefix)
                            .send(sender);
            }
        }
        return true;
    }
}
