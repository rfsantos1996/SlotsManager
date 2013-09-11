package com.jabyftw.slotsmng;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

public class PreLoginListener implements Listener {
    private SlotManager plugin;
    
    PreLoginListener(SlotManager plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerPreLoginEvent e) {
        if(e.getResult() == Result.KICK_FULL && plugin.maxPlayers < plugin.getServer().getOnlinePlayers().length)
            e.allow();
    }
}
