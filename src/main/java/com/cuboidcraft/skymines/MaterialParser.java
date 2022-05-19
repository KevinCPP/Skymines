package com.cuboidcraft.skymines;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

public class MaterialParser {
    //disable constructor
    @SuppressWarnings("unused")
    private MaterialParser() {}

    public static Material[] materialMapToArray(EnumMap<Material, Integer> input){
        System.out.println("materialMapToArray debug");

        int total = 0;
        for(EnumMap.Entry<Material, Integer> e : input.entrySet()){
            total += e.getValue();
            System.out.println(e.toString());
        }

        ArrayList<Material> materialList = new ArrayList<>(100);

        for(EnumMap.Entry<Material, Integer> e : input.entrySet()){
            int amount = (int)Math.floor(100.f * ((float)e.getValue()/(float)total));
            for(int i = 0; i < amount; i++){
                materialList.add(e.getKey());
            }
        }

        Collections.shuffle(materialList);

        Material[] materialArray = new Material[materialList.size()];
        for(int i = 0; i < materialList.size(); i++){
            materialArray[i] = materialList.get(i);
        }

        return materialArray;
    }

    //format: x1%Material1,x2%Material2... x1+x2+... = 100
    public static EnumMap<Material, Integer> strToMap(String s){
        EnumMap<Material, Integer> map = new EnumMap<>(Material.class);
        //if there are no commas, then there must only be one material
        if(!s.contains(",")){
            //try to turn the string into a material, if it can't be
            //done, m will be null
            Material m = Material.getMaterial(s.toUpperCase());
            if(m != null)
                map.put(m, 100);

            return map; //will return an empty map if m == null
        }

        if(!s.contains("%"))
            map = mode1(s);
        else
            map = mode2(s);

        return map;
    }

    //has no percentage
    private static EnumMap<Material, Integer> mode1(String s){
        EnumMap<Material, Integer> map = new EnumMap<>(Material.class);
        String[] arr = s.split(",");
        ArrayList<Material> materials = new ArrayList<>();

        for(String str : arr){
            Material m = Material.getMaterial(str.toUpperCase());
            if(m != null)
                materials.add(m);
        }

        //since no percent was provided, it will just be an equal
        //percent among all of them. We will get this by just dividing
        //100 by the amount of materials. For example, if the following
        //string was passed: STONE,COBBLESTONE,ANDESITE,DIORITE,GRANITE
        //then it would return a map with (STONE, 20), (COBBLESTONE, 20)...
        int percent = 100 / materials.size();

        //build the map by putting each material and it's equal share of
        //the percent
        for(Material m : materials){
            if(map.containsKey(m)){
                map.put(m, map.get(m) + percent);
            }
            map.put(m, percent);
        }

        //it's possible due to truncation, that the percent will not add up
        //to 100, we will fix that with a method fix(); although fixing it
        //is optional as it should work fine with the rest of the program
        return map;
    }

    private static EnumMap<Material, Integer> mode2(String s){
        //map that we are going to build
        EnumMap<Material, Integer> map = new EnumMap<>(Material.class);

        //split the string at every % and , symbol
        String[] arr = s.split("[%,]");

        //two arraylists which will be turned into the map
        ArrayList<Integer> percents = new ArrayList<>();
        ArrayList<Material> materials = new ArrayList<>();

        //The syntax for this is "10%stone,20%cobblestone,30%dirt,40%andesite" where the percents should
        //add up to 100, and each string following the percent symbol should be a valid material. When we
        //split this string into an array of strings at every % and , symbol, that should result in an
        //array like ["10", "stone", "20", "cobblestone"...], and for that, every even index (0, 2, ...)
        //should be an integer (percentage), and index+1 (every odd index) should be the corresponding
        //material. That is how we will parse it. Every even number will be parsed into an int, and every
        //odd number will be parsed into a material. In theory, this should result in two arraylists of
        //equal size percents and materials, which we can use to build the EnumMap
        for(int i = 0; i < arr.length; i ++) {
            if((i % 2) == 0) {
                try {
                    percents.add(Integer.parseInt(arr[i]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Material m = Material.getMaterial(arr[i].toUpperCase());
                if(m != null)
                    materials.add(m);
            }
        }

        //if we couldn't parse any materials, just return the default of 100% stone
        if(materials.size() == 0){
            map.put(Material.STONE, 100);
            return map;
        }

        //if there are an equal amount of percents and materials, that means
        //the syntax was correct and we can go ahead and build the EnumMap based
        //off of that. So "10%cobblestone, 20%dirt, 70%stone" will turn into a
        //EnumMap with (cobblestone, 10), (dirt, 20), (stone, 70)
        if(percents.size() == materials.size()){
            for(int i = 0; i < materials.size(); i++){
                if(!map.containsKey(materials.get(i)))
                    map.put(materials.get(i), percents.get(i));
                else
                    map.put(materials.get(i), map.get(materials.get(i)) + percents.get(i));
            }

            return map;
        }

        //if the syntax is wrong, we'll just go with an equal distribution with all
        //of the materials we were able to parse
        int percent = 100 / materials.size();

        for(Material m : materials){
            map.put(m, percent);
        }

        //finally return the map
        return map;
    }

}
