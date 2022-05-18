package com.cuboidcraft.skymines;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Skymines extends JavaPlugin {

    @Override
    public void onEnable() {
        new Utility(this);
        Bukkit.getPluginManager().registerEvents(new MineFactory(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
