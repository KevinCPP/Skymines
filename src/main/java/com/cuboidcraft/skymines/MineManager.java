package com.cuboidcraft.skymines;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*
    This class will load all mines into memory when the plugin is enabled, and
    when the plugin is disabled, it will save them all into Gson files...
 */
public class MineManager {
    //all mines that we have loaded into memory. Hashmap for quick lookup by player UUID
    private HashMap<UUID, Mine> loadedMines = new HashMap<>();

    //this plugin, will be used for getting the path to the Data folder
    private Plugin plugin;

    //folder that data for the mines will be stored in
    private String minesPath;

    //disable default constructor for dependency injection
    private MineManager(){}

    Gson gson;

    //new MineManager with no registered mines
    public MineManager(@NotNull Plugin pl){
        minesPath = pl.getDataFolder().getAbsolutePath() + File.separator + "mines";
        plugin = pl;
        setupGson();
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

    private void setupGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(new TypeToken<EnumMap<Material, Integer>>() {}.getType(), new Utility.EnumMapInstanceCreator<Material, Integer>(Material.class)).create();
        gson = builder.create();
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
            //first, if the path doesn't exist where the mines
            //data should be stored the path will be created
            File file = new File(minesPath); //new file with the path
            file.mkdirs(); //make directory at that path

            //loop through each mine that is loaded into memory
            for (Map.Entry<UUID, Mine> entry : loadedMines.entrySet()) {
                //get path to the file we are going to write, format will be /mines/<playerUUID>.json
                String path = minesPath + File.separator + entry.getKey() + ".json";
                //create a new filewriter object that gson will use to write the file
                FileWriter writer = new FileWriter(new File(path));
                //convert the MineData object into a json string and write it to the file
                gson.toJson(entry.getValue().getMineData(), writer);
                //writer must be flushed and closed, or changes to the file will not propagate
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            //if for whatever reason writing the JSON file failed, print an error
            System.out.println("could not save mine to gson file.");
        }
    }


    //TODO: finish implementing loadMines()
    //load all mines in the folder from the disk (using GSON)
    public void loadMines(){
        try {
            //list of all files inside the minesPath directory
            File[] mineFiles = new File(minesPath).listFiles();
            //if there are no mines to load, then do nothing
            if (mineFiles == null || mineFiles.length == 0)
                return;

            //loop through each file in the mines folder
            for (File file : mineFiles) {
                Reader reader = Files.newBufferedReader(file.toPath());
                MineData data = gson.fromJson(reader, MineData.class);
                registerMine(data);
            }
        } catch(IOException e){
            System.out.println("could not load mines from GSON");
        }
    }
}
