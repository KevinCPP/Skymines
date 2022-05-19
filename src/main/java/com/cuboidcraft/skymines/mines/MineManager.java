package com.cuboidcraft.skymines.mines;

import com.cuboidcraft.skymines.util.JsonHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
    This class will load all mines into memory when the plugin is enabled, and
    when the plugin is disabled, it will save them all into Gson files...
 */
public class MineManager {
    //will do all of our saving/loading with files
    private JsonHelper jsonHelper;
    //all mines that we have loaded into memory. Hashmap for quick lookup by player UUID
    private HashMap<UUID, Mine> loadedMines = new HashMap<>();
    //this plugin, will be used for getting the path to the Data folder
    private Plugin plugin;
    //folder that data for the mines will be stored in
    private String minesPath;
    //disable default constructor for dependency injection
    private MineManager(){}

    //new MineManager with no registered mines
    public MineManager(@NotNull Plugin pl){
        plugin = pl;
        minesPath = pl.getDataFolder().getAbsolutePath() + File.separator + "mines";
        jsonHelper = new JsonHelper();
        loadMines();
    }

    //new MineManager, but initialize loadedMines from another MineManager's loadedMines
    public MineManager(@NotNull Plugin pl, @NotNull MineManager mgr){
        this(pl);
        loadedMines = new HashMap<>(mgr.loadedMines);
    }

    //new MineManager, but initialize loadedMines directly
    public MineManager(@NotNull Plugin pl, @NotNull HashMap<UUID, Mine> mines) {
        this(pl);
        loadedMines = new HashMap<>(mines);
    }

    //register a new mine
    public void registerMine(@NotNull Mine mine){
        loadedMines.put(mine.getMineData().getOwner(), mine);
    }

    //register a new mine by building it from a MineData
    public void registerMine(@NotNull MineData data){
        loadedMines.put(data.getOwner(), new Mine(data));
    }

    //reset the mine of player
    public boolean resetMine(UUID player) {
        if(loadedMines.containsKey(player)) {
            loadedMines.get(player).reset();
            return true;
        }

        return false;
    }

    //get the player's mine
    public Mine getMine(UUID player){
        return loadedMines.get(player);
    }

    //save all of the mines to the disk (using GSON)
    public void saveMines() {
        try {
            for (HashMap.Entry<UUID, Mine> e : loadedMines.entrySet()) {
                jsonHelper.saveToFile(minesPath, e.getKey().toString(), e.getValue().getMineData());
            }
        } catch (IOException e) {
            Bukkit.getLogger().warning("Issue saving mines");
        }
    }


    //load all mines in the folder from the disk (using GSON)
    public void loadMines(){
        try {
            ArrayList<MineData> list = jsonHelper.loadFromPath(minesPath, MineData.class);
            for(MineData dat : list) {
                if(dat != null) {
                    registerMine(dat);
                }
            }
        } catch (IOException e){
            Bukkit.getLogger().warning("Issue loading mines");
        }
    }
}
