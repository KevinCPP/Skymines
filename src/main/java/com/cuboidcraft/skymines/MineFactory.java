package com.cuboidcraft.skymines;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.api.IslandCreateEvent;
import com.iridium.iridiumskyblock.api.IslandDeleteEvent;
import com.iridium.iridiumskyblock.api.IslandSettingChangeEvent;
import com.iridium.iridiumskyblock.database.Island;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


import java.util.Optional;

public class MineFactory implements Listener {

    public MineFactory(){

    }

    @EventHandler
    public void onContainerOpened(InventoryCloseEvent e){

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onIslandCreate(IslandCreateEvent e) {
        //print 10 times so i don't miss it
        for(int i = 0; i < 10; i++)
            System.out.println("The event ran");

        Utility.getInst().log("Debug - Cancelled: " + e.isCancelled());

        Optional<Island> is = IridiumSkyblockAPI.getInstance().getIslandByName(e.getIslandName());
        Location endCenter = is.get().getCenter(IridiumSkyblockAPI.getInstance().getEndWorld());

        Utility.getInst().log(endCenter.toString());
    }

    @EventHandler
    public void onIslandDelete(IslandDeleteEvent e) {
        System.out.println("an island was deleted!");
    }

    @EventHandler
    public void onSettingchanged(IslandSettingChangeEvent e) {
        System.out.println("setting changed");
    }
}
