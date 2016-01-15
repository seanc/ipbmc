package me.imsean.ipbmc.listeners;

import me.imsean.ipbmc.message.Message;
import me.imsean.ipbmc.message.MessageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sean on 1/15/16.
 */
public class PMListener implements Listener {

    private boolean listening;
    private Player player, target;
    private StringBuilder message;
    private Plugin plugin;

    public PMListener(boolean listening, Player player, Player target, Plugin plugin) {
        this.listening = listening;
        this.player = player;
        this.target = target;
        this.message = new StringBuilder();
        this.plugin = plugin;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(!this.listening) return;
        if(!e.getPlayer().getUniqueId().equals(this.player.getUniqueId())) return;
        if(e.getMessage().equalsIgnoreCase("send")) {
            e.setCancelled(true);
            this.listening = false;

            Message.sendMessage(this.player, MessageType.PRIMARY, "Sending message...");
            try {
                URL url = new URL(this.plugin.getConfig().getString("api_url"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                String params = "mcpm_from=" + this.player.getName() + "&mcpm_to=" + this.target.getName() + "&message=" + message.toString();

                DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
                writer.writeBytes(params);
                writer.flush();
                writer.close();

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();

                this.player.sendMessage(response.toString());
                System.out.println(response.toString());
            } catch (IOException ex) {
                Message.sendMessage(this.player, MessageType.ERROR, "An error occurred while trying to send the message, this has been logged.");
                ex.printStackTrace();
            }
            e.getPlayer().sendMessage(this.message.toString());
        } else if(e.getMessage().equalsIgnoreCase("cancel")) {
            e.setCancelled(true);
            this.listening = false;
            this.message = null;
            Message.sendMessage(this.player, MessageType.PRIMARY, "Message cancelled");
        } else {
            e.setCancelled(true);
            Message.sendMessage(player, MessageType.RAW, "&6Added line: &e" + e.getMessage());
            this.message.append(e.getMessage()).append(System.lineSeparator());
        }
    }

}
