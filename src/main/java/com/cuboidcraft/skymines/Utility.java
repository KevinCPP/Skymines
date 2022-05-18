package com.cuboidcraft.skymines;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;

public class Utility {

    private static Utility instance;

    private JavaPlugin plugin;

    private Utility() {}

    public Utility(JavaPlugin pl){
        if(instance != null)
            return;

        plugin = pl;
        instance = this;
    }

    public static Utility getInst(){
        return instance;
    }

    public Collection<? extends Player> getOnlinePlayers(){
        panic();
        return plugin.getServer().getOnlinePlayers();
    }

    public World getWorld(String s){
        panic();
        return plugin.getServer().getWorld(s);
    }

    public void log(String s){
        panic();
        plugin.getLogger().info(s);
    }

    private void panic(){
        if(plugin == null || instance == null)
            for(int i = 0; i < 20; i++)
                System.out.println("UTILITY CLASS NOT SET UP CORRECTLY.");
    }
}
