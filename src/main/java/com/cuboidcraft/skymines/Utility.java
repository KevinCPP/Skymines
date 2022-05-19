package com.cuboidcraft.skymines;

import com.google.gson.InstanceCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;

public class Utility {

    private static Utility instance;

    private Plugin plugin;

    private Utility() {}

    public HashMap<Material, Integer> defaultMats;

    public Utility(Plugin pl){
        if(instance != null)
            return;

        defaultMats = new HashMap<Material, Integer>();
        defaultMats.put(Material.STONE, 100);
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

    public Plugin getPlugin(){
        return plugin;
    }

    private void panic(){
        if(plugin == null || instance == null)
            for(int i = 0; i < 20; i++)
                System.out.println("UTILITY CLASS NOT SET UP CORRECTLY.");
    }

    static class EnumMapInstanceCreator<K extends Enum<K>, V> implements InstanceCreator<EnumMap<K, V>> {
        private final Class<K> enumclass;

        public EnumMapInstanceCreator(final Class<K> enumc){
            super();
            this.enumclass = enumc;
        }

        @Override
        public EnumMap<K, V> createInstance(final Type type){
            return new EnumMap<K, V>(enumclass);
        }
    }
}
