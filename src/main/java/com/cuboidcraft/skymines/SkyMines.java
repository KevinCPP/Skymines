package com.cuboidcraft.skymines;

import com.cuboidcraft.skymines.mines.MineFactory;
import com.cuboidcraft.skymines.mines.MineListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class SkyMines extends JavaPlugin {

    private MineFactory mineFactory;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.getCommand("boxtest").setExecutor(new Commands(this));
        this.getCommand("resetmine").setExecutor(new Commands(this));
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
