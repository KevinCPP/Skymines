package com.cuboidcraft.skymines;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.api.IslandCreateEvent;

import com.iridium.iridiumskyblock.database.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MineFactory extends BukkitRunnable implements Listener {

    Plugin plugin;

    private MineFactory(){}

    public MineFactory(Plugin pl){
        plugin = pl;
    }

    //this function will process everyone in names, and it will create the mines on their island.
    @Override
    public void run(){

    }
}
