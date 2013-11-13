package com.jabyftw.slotsmng;

import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SlotManager extends JavaPlugin implements CommandExecutor {

    public int maxPlayers;

    @Override
    public void onEnable() {
        genConfig();
        getCommand("slotsmanager").setExecutor(this);
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
        getServer().getPluginManager().registerEvents(new PreLoginListener(this), this);
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

    private void genConfig() {
        FileConfiguration config = getConfig();
        config.addDefault("config.invisibleMaxPlayersAllowed", 32);
        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        maxPlayers = config.getInt("config.invisibleMaxPlayersAllowed");
    }

    private class PreLoginListener implements Listener {

        private final SlotManager plugin;

        PreLoginListener(SlotManager plugin) {
            this.plugin = plugin;
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onLogin(PlayerPreLoginEvent e) {
            if (e.getResult() == PlayerPreLoginEvent.Result.KICK_FULL && plugin.maxPlayers < plugin.getServer().getOnlinePlayers().length) {
                e.allow();
            }
        }
    }

    private class LoginListener implements Listener {

        private final SlotManager plugin;

        LoginListener(SlotManager plugin) {
            this.plugin = plugin;
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onLogin(PlayerLoginEvent e) {
            if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL && plugin.maxPlayers < plugin.getServer().getOnlinePlayers().length) {
                e.allow();
            }
        }
    }

}
