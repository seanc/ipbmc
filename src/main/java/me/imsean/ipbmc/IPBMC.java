package me.imsean.ipbmc;

import me.imsean.ipbmc.commands.PMCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by sean on 1/13/16.
 */
public class IPBMC extends JavaPlugin {

    private Plugin plugin;

    @Override
    public void onEnable() {
        this.plugin = this;

        final FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();

        this.setupCommands();
    }

    private void setupCommands() {
        this.getCommand("pm").setExecutor(new PMCommand(this.plugin));
    }
}
