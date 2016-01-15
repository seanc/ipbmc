package me.imsean.ipbmc.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by sean on 1/13/16.
 */
public class Message {

    private static String PREFIX = "&6[&eIPBMC&6] ";

    public static void sendMessage(final Player player, final MessageType type, String message) {
        switch (type) {
            case DEFAULT:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + message));
                break;
            case PRIMARY:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "&e" + message));
                break;
            case WARNING:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "&c" + message));
                break;
            case ERROR:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "&4" + message));
                break;
            default:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

}
