package com.jabyftw.slotsmng;

import java.io.File;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SlotManager extends JavaPlugin implements CommandExecutor {
    int cVersion = 1;
    private Config nConfig;
    private File folder = new File("plugins" + File.separator + "RealTime");
    
    int maxPlayers;

    @Override
    public void onEnable() {
        folder.mkdirs();
        nConfig = new Config(this);
        nConfig.createConfig();
        setConfig();
        
        getCommand("slotsmanager").setExecutor(this);
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
        getServer().getPluginManager().registerEvents(new PreLoginListener(this), this);
        log("Running!", 0);
    }

    @Override
    public void onDisable() {}
    
    void setConfig() {
        FileConfiguration config = getConfig();
        maxPlayers = config.getInt("config.invisiblePlayerLimit");
        log("Configured!", 0);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("slotsmanager.change")) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /slotsmanager (slots)");
                return false;
            }
            try {
                maxPlayers = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "The '/slotsmanager (SLOTS)' must be a number");
                return false;
            }
            sender.sendMessage(ChatColor.BLUE + "Done! (;");
            return true;
        }
        return false;
    }
    
    /*
     * 0 - normal
     * 1 - warning
     * 2 - debug NOT NEEDED
     */
    void log(String msg, int mode) {
        if(mode == 0)
            this.getLogger().log(Level.INFO, msg);
        else if(mode == 1)
            this.getLogger().log(Level.WARNING, msg);
    }
}