package com.cuboidcraft.skymines.util;

import com.cuboidcraft.skymines.mines.Mine;
import com.cuboidcraft.skymines.mines.MineData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;

public class JsonHelper {

    private static final String fileSuffix = ".json";
    private Type enumMapTypeToken;
    private EnumMapInstanceCreator<Material, Integer> enumMapInstanceCreator;
    private Gson gson;

    public JsonHelper(){
        enumMapTypeToken = new TypeToken<EnumMap<Material, Integer>>() {}.getType();
        enumMapInstanceCreator = new EnumMapInstanceCreator<>(Material.class);
        setupGson();
    }

    //sets up Gson from a GsonBuilder with the proper settings
    private void setupGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(enumMapTypeToken, enumMapInstanceCreator).create();
        gson = gsonBuilder.create();
    }

    public <T> ArrayList<T> loadFromPath(String path, Class<T> classOfT) throws IOException {
        //list where result will be stored
        ArrayList<T> result = new ArrayList<T>();
        //get a list of files in the path
        File[] files = new File(path).listFiles();
        //if files is null or the length is 0, then something
        //is wrong with the path, just return empty list
        if(files == null || files.length == 0)
            return result;

        //loop through each Json file in the path and build an object from it.
        Reader reader;
        for(File file : files){
            reader = Files.newBufferedReader(file.toPath());
            T obj = gson.fromJson(reader, classOfT);
            result.add(obj);
        }

        return result;
    }

    /**
     * This function will construct an object from a json file given the path to the file,
     * and what type of object it should be trying to construct.
     * @param path      Path to the file you want to read
     * @param classOfT  class of object you want to try to parse from the file
     * @return          will return null if it fails, or the object that it was able to parse
     * @param <T>       Type of object you are trying to parse
     * @throws IOException  throws an IOException if anything goes wrong with java.io.file (reading/writing)
     */
    public <T> T loadFromFile(String path, Class<T> classOfT) throws IOException {
        File file = new File(path);

        if(file == null){
            return null;
        }

        Reader reader = Files.newBufferedReader(file.toPath());
        T obj = gson.fromJson(reader, classOfT);

        return obj;
    }

    /**
     *
     * @param path      folder that the file should be saved in
     * @param fileName  what the name of the file should be (.json suffix automatically added)
     * @param obj       the object you're trying to save
     * @param <T>       what type of object you are trying to save
     * @throws IOException  throws an exception if anything goes wrong with java.io.file (reading/writing)
     */
    public <T> void saveToFile(String path, String fileName, T obj) throws IOException {
        //first create the directory to path if it doesn't exist
        File file = new File(path);
        file.mkdirs();

        //then create a filewriter at the path of the actual file we are writing to
        String filePath = path + File.separator + fileName + fileSuffix;
        FileWriter writer = new FileWriter(new File(filePath));

        //use gson to serialize obj and write it to the file
        gson.toJson(obj, writer);
        //these two methods must be called on writer, or it won't save the changes
        writer.flush();
        writer.close();
    }

}
