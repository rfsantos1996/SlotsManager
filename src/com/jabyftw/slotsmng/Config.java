package com.jabyftw.slotsmng;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    SlotManager plugin;
    public FileConfiguration ConfigConfiguration;
    public File ConfigFile;
    
    public Config(SlotManager plugin) {
        this.plugin = plugin;
        ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        ConfigConfiguration = YamlConfiguration.loadConfiguration(ConfigFile);
    }
    
    public void createConfig() {
        ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!ConfigFile.exists()) {
            try {
                ConfigFile.createNewFile();
                generateConfig();
            } catch (IOException e) {
                plugin.log("Could not create the config file: " + e, 1);
            }
        }
    }
    
    public void deleteConfig() {
        ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if(ConfigFile.exists()) {
            ConfigFile.delete();
        }
    }
    
    public void generateConfig() {
        try {
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            FileWriter w = new FileWriter(configFile);
            w(w, "config:");
            w(w, "  # This will change the player limit");
            w(w, "  # But the 'x/y players online' will still be the same");
            w(w, "  # You can change temporally the number of slots using: /slotsmanager (slots)");
            w(w, "  # This will require the permission: 'slotsmanager.change'");
            w(w, "  invisiblePlayerLimit: 32");
            w(w, "\n");
            w(w, "DoNotChangeOrItWillEraseYourConfig:");
            w(w, "  configVersion: " + plugin.cVersion);
            w.close();
            reloadConfig();
        } catch (IOException e) {
            plugin.log("Could not generate the config file: " + e, 1);
        }
    }
    
    private void w(FileWriter writer, String string) throws IOException {
        writer.write(string + "\n");
    }
    
    public void reloadConfig() {
        if (!ConfigFile.exists()) {
            ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        }
        ConfigConfiguration = YamlConfiguration.loadConfiguration(ConfigFile);
        
        InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            ConfigConfiguration.setDefaults(defConfig);
        }
    }
    
    public FileConfiguration getConfig() {
        if (ConfigConfiguration == null) {
            reloadConfig();
        }
        return ConfigConfiguration;
    }
    
    public void saveConfig() {
        if (ConfigConfiguration == null || ConfigFile == null) {
            return;
        }
        
        try {
            ConfigConfiguration.save(ConfigFile);
        } catch (IOException e) {
            plugin.log("Could not save the config file to the disk: " + e, 1);
        }
    }
}