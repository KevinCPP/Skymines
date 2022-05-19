package com.cuboidcraft.skymines;

import org.bukkit.Location;
import java.util.Iterator;

public class BoxIterator implements Iterator<Location> {
    private Box box;
    int x, y, z;
    Location first;

    //disable default constructor
    @SuppressWarnings("unused")
    private BoxIterator() {}

    public BoxIterator(Box b){
        box = b;
        x = b.minX;
        y = b.minY;
        z = b.minZ;
        first = new Location(b.world,x,y,z);
    }

    @Override
    public boolean hasNext() {
        return x != box.maxX || y != box.maxY || z != box.maxZ;
    }

    @Override
    public Location next() {
        if(first != null) {
            Location f = new Location(first.getWorld(), first.getX(), first.getY(), first.getZ());
            first = null;
            return f;
        }

        if(z != box.maxZ){
            return new Location(box.world, x, y, ++z);
        } else if(y != box.maxY){
            z = box.minZ;
            return new Location(box.world, x, ++y, z);
        } else if(x != box.maxX){
            y = box.minY;
            z = box.minZ;
            return new Location(box.world, ++x, y, z);
        }

        return null;
    }
}