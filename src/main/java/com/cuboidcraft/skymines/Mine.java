package com.cuboidcraft.skymines;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Mine {
    private Box mineBounds;
    private Location spawnLoc;

    private String mineName;

    HashMap<Material, Integer> makeup = new HashMap<>();

    //disable default constructor
    private Mine() {}

    public Mine(Box bounds, HashMap<Material, Integer> materials, String name){
        mineBounds = bounds;
        makeup = new HashMap<>(materials);
        spawnLoc = mineBounds.getTopMiddle();
        mineName = name;
    }

    public void setMakeup(HashMap<Material, Integer> materials){
        makeup = new HashMap<>(materials);
    }

    public void setSpawn(Location l) {
        spawnLoc = l;
    }

    //resets the mine
    public boolean reset(){
        teleportPlayersOut();
        mineBounds.fill(makeup);

        return true;
    }

    public void setName(String name){
        mineName = name;
    }

    public String getName(){
        return new String(mineName);
    }

    public boolean nameEquals(String name){
        return mineName.equals(name);
    }

    //loop through all players and teleport them outside the mine.
    public void teleportPlayersOut(){
        for(Player p : Utility.getInst().getOnlinePlayers()){
            //get the location of the player
            Location l = p.getLocation();
            //test if the player is in the same world
            if(!l.getWorld().equals(mineBounds.world))
                continue;
            //if they're in the same world, test if they're
            //inside the mine's bounding box
            if(!mineBounds.isInside(l))
                continue;
            //if so, teleport them to the spawnLoc (outside the mine)
            p.teleport(spawnLoc);
        }
    }

}
