package com.cuboidcraft.skymines;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Box {
   public Location pos1, pos2;

   World world;
   int minX, minY, minZ, maxX, maxY, maxZ;

   private Box() {}

   public Box(Location l1, Location l2){
       pos1 = l1;
       pos2 = l2;
       world = l1.getWorld();

       //default world if they're not equal for some reason
       if(!l1.getWorld().equals(l2.getWorld()))
           world = Utility.getInst().getWorld("world");

       //set all of these variables because they're kind of useful
       //in calculations across multiple different functions
       minX = Math.min(l1.getBlockX(), l2.getBlockX());
       minY = Math.min(l1.getBlockY(), l2.getBlockY());
       minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());
       maxX = Math.max(l1.getBlockX(), l2.getBlockX());
       maxY = Math.max(l1.getBlockY(), l2.getBlockY());
       maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());
   }

   public boolean isInside(Location l){
       if(maxX < l.getBlockX()) return false;
       if(maxY < l.getBlockY()) return false;
       if(maxZ < l.getBlockZ()) return false;
       if(l.getBlockX() < minX) return false;
       if(l.getBlockY() < minY) return false;
       if(l.getBlockZ() < minZ) return false;

       return true;
   }

   public Location getTopMiddle(){
       int midX = (minX+maxX)/2;
       int midZ = (minZ+maxZ)/2;
       return new Location(world, midX, maxY, midZ, 0, 0);
   }

   public boolean fill(HashMap<Material, Integer> mats){
       Material[] materials = MaterialParser.matsToArr(mats);

       int i = 0;
       for(int x = minX; x <= maxX; x++){
           for(int y = minY; y <= maxY; y++){
               for(int z = minZ; z <= maxZ; z++){
                   Location curr = new Location(world, x, y, z);

                   if(!world.getBlockAt(curr).isEmpty())
                       continue;

                   curr.getBlock().setType(materials[i]);

                   i++;
                   if(i >= materials.length)
                       i = 0;
               }
           }
       }

       return true;
   }
}

