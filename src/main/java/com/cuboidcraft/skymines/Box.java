package com.cuboidcraft.skymines;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.*;

public class Box implements Iterable<Location> {
   public Location pos1, pos2;

   World world;
   int minX, minY, minZ, maxX, maxY, maxZ;

   //disable default constructor
   @SuppressWarnings("unused")
   private Box() {}

   public Box(Location l1, Location l2){
       pos1 = l1;
       pos2 = l2;

       if(l1.getWorld() == null || l2.getWorld() == null)
           world = null;
       else if(l1.getWorld() != null && l2.getWorld() != null)
           world = l2.getWorld();
       else
           world = l1.getWorld() == null ? l2.getWorld() : l1.getWorld();

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

   public boolean isWithinNblocksOfEdge(Location loc, int N, boolean testY){
       //we only want to test inside of the box
       if(!isInside(loc))
           return false;

       //test if X is within N blocks of the edge
       if(loc.getBlockX() < minX+N || maxX-N < loc.getBlockX())
           return true;

       //test if Y is within N blocks of the edge
       if(loc.getBlockZ() < minZ+N || maxZ-N < loc.getBlockZ())
           return true;

       //if testY is true, we will test the distance like we normally do for X and Z
       //(testY allows us to have thick walls but a 1 block floor)
       //we also only test minY because if we test maxY, it'll make a ceiling of bedrock
       if(testY && loc.getBlockY() < minY+N)
           return true;

       //we only want a thickness of 1 on the y layer if testY is false
       if(!testY && loc.getBlockY() == minY)
           return true;

       //if none of those checks passed, it's not within N blocks of the edge
       return false;
   }

   public Location getTopMiddle(){
       //find the middle block on the XZ axis
       int midX = (minX+maxX)/2;
       int midZ = (minZ+maxZ)/2;

       //add 0.5 to midX and midZ so players are teleported to the middle of the block
       //add 1 to maxY so that players are teleported above the top layer, not inside of it.
       return new Location(world, (midX + 0.5f), maxY+1, (midZ + 0.5f), 0, 0);
   }

    @NotNull
    @Override
    public Iterator<Location> iterator() {
        return new BoxIterator(this);
    }
}

