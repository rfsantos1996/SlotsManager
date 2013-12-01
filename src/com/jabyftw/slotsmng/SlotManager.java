package com.jabyftw.slotsmng;

import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SlotManager extends JavaPlugin implements CommandExecutor, Listener {

    private int maxPlayers;

    @Override
    public void onEnable() {
        getConfig().addDefault("config.invisibleMaxPlayersAllowed", 32);
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        maxPlayers = getConfig().getInt("config.invisibleMaxPlayersAllowed");
        getCommand("slotsmanager").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().log(Level.INFO, "Running!");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        } else if (sender.hasPermission("slotsmanager.change")) {
            try {
                maxPlayers = Integer.parseInt(args[0]);
                sender.sendMessage(ChatColor.BLUE + "Done! (;");
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You dont have permission!");
            return true;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL && maxPlayers < getServer().getOnlinePlayers().length) {
            e.allow();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerPreLoginEvent e) {
        if (e.getResult() == PlayerPreLoginEvent.Result.KICK_FULL && maxPlayers < getServer().getOnlinePlayers().length) {
            e.allow();
        }
    }
}
