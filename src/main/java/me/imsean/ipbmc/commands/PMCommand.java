package me.imsean.ipbmc.commands;

import me.imsean.ipbmc.message.Message;
import me.imsean.ipbmc.message.MessageType;
import me.imsean.ipbmc.listeners.PMListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by sean on 1/13/16.
 */
public class PMCommand implements CommandExecutor {

    private final Plugin plugin;

    public PMCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                StringBuilder help = new StringBuilder();
                help.append("&6-----------[&eIPBMC Help&6]-----------").append(System.lineSeparator());
                help.append("&6/pm <username>").append(System.lineSeparator());
                Message.sendMessage(player, MessageType.RAW, help.toString());
            }
            if(args.length > 0) {
                Player target = Bukkit.getPlayer(args[0].trim());
                PMListener pml = new PMListener(true, player, target, this.plugin);
                Bukkit.getServer().getPluginManager().registerEvents(pml, this.plugin);

                Message.sendMessage(player, MessageType.PRIMARY, "Started new message");
                Message.sendMessage(player, MessageType.PRIMARY, "Start typing to add lines to your message");
                Message.sendMessage(player, MessageType.PRIMARY, "Type &6cancel &eor &6send &eto cancel or send a message");
            }
        }
        return true;
    }
}
