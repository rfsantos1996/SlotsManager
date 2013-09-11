package com.jabyftw.slotsmng;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginListener implements Listener {
    private SlotManager plugin;
    
    LoginListener(SlotManager plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent e) {
        if(e.getResult() == Result.KICK_FULL && plugin.maxPlayers < plugin.getServer().getOnlinePlayers().length)
            e.allow();
    }
}
