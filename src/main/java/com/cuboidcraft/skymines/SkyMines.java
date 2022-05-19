package com.cuboidcraft.skymines;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class SkyMines extends JavaPlugin {

    private MineFactory mineFactory;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        new Utility(this);
        this.getCommand("boxtest").setExecutor(new Commands());
        this.getCommand("resetmine").setExecutor(new Commands());
    }

    public void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MineListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
