package com.cuboidcraft.skymines.mines;

import com.cuboidcraft.skymines.util.Box;
import com.cuboidcraft.skymines.util.MaterialParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MineData {
    //Player who owns the mine
    private UUID Owner;


    //the bounding box and string can be used to construct a box object,
    //primitives are used to make serialization easier.
    //coordinates that describe the bounding box of the mine
    int minX, minY, minZ, maxX, maxY, maxZ;
    //world that the mine is in
    private String worldName;

    //type of mine
    private String mineType;
    private int borderThickness = 1;
    private EnumMap<Material, Integer> materials;

    public MineData(@NotNull UUID owner, @NotNull Box bounds, @NotNull String type){
        Owner = owner;
        mineType = type;
        materials = MaterialParser.strToMap(type);

        setBox(bounds);

        //list all of the materials
        System.out.println("Debug (created MineData):");
        for(Material material : materials.keySet()){
            System.out.println(material.toString());
        }
    }

    public UUID getOwner() {
        return Owner;
    }
    public void setOwner(UUID owner) {
        Owner = owner;
    }
    public Box getBox() {
        Location min = new Location(Bukkit.getWorld(worldName), minX, minY, minZ);
        Location max = new Location(Bukkit.getWorld(worldName), maxX, maxY, maxZ);
        return new Box(min, max);
    }
    public void setBox(Box b) {
        minX = b.minX;
        minY = b.minY;
        minZ = b.minZ;
        maxX = b.maxX;
        maxY = b.maxY;
        maxZ = b.maxZ;
        worldName = b.world.getName();
    }
    public String getMineType() {
        return mineType;
    }
    public void setMineType(String minetype) {
        mineType = minetype;
    }
    public int getBorderThickness() {
        return borderThickness;
    }
    public boolean setBorderThickness(int borderThickness) {
        int width = maxX - minX;
        int length = maxZ - minZ;

        if(borderThickness >= Math.abs(width)/2)
            return false;

        if(borderThickness >= Math.abs(length)/2)
            return false;

        if(borderThickness < 0)
            return false;

        this.borderThickness = borderThickness;
        return true;
    }
    public EnumMap<Material, Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(EnumMap<Material, Integer> materials) {
        this.materials = new EnumMap<>(materials);
    }

}
