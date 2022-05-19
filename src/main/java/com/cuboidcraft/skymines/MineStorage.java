package com.cuboidcraft.skymines;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class MineStorage {
    private HashMap<UUID, Mine> mines;
    Plugin plugin;

    public MineStorage(SkyMines pl){
        mines = new HashMap<>();
        plugin = pl;
    }

    public int minesCount() {
        return mines.size();
    }

    public void addMine(UUID uuid, Mine mine){
        mines.putIfAbsent(uuid, mine);
    }

    public void removeMine(UUID uuid){
        mines.remove(uuid);
    }

    public void replaceMine(UUID uuid, Mine mine){
        mines.replace(uuid, mine);
    }

    public HashMap<UUID, Mine> getMines(){
        return mines;
    }

    public Mine getMine(UUID uuid){
        return mines.get(uuid);
    }

    public boolean hasMine(UUID uuid){
        return mines.containsKey(uuid);
    }
}
