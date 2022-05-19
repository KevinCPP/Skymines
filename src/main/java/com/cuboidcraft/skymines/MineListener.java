package com.cuboidcraft.skymines;

import com.iridium.iridiumskyblock.api.IslandCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class MineListener implements Listener {
    Plugin plugin;
    ArrayList<String> names = new ArrayList<>();

    private MineListener(){}

    public MineListener(Plugin pl){
        plugin = pl;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onIslandCreate(IslandCreateEvent e) {
        Utility.getInst().log("island was created");
        names.add(e.getUser().getName());


    }
}
