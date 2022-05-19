package com.cuboidcraft.skymines;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;

public class MaterialBag {
    private static final Material defaultMaterial = Material.STONE;
    EnumMap<Material, Integer> materialMap;
    Material[] materialArray;

    int currentIndex = 0;

    //default constructor will just have stone as the only material
    public MaterialBag(){
        materialMap = new EnumMap<>(Material.class);
        materialMap.put(defaultMaterial, 100);
        materialArray = MaterialParser.materialMapToArray(materialMap);
    }

    //can pass a list of materials and probabilities to pick from
    public MaterialBag(EnumMap<Material, Integer> materials){
        materialMap = new EnumMap<>(materials);
        materialArray = MaterialParser.materialMapToArray(materialMap);
    }

    //can build a list of materials and probabilities from a string
    public MaterialBag(String materials){
        materialMap = MaterialParser.strToMap(materials);
        materialArray = MaterialParser.materialMapToArray(materialMap);
    }

    public int getNumUniqueMaterials(){
        return materialMap.size(); //return the amount of unique materials
    }

    public EnumMap<Material, Integer> getMaterialMap(){
        return new EnumMap<>(materialMap); //return a copy
    }

    public Material nextMaterial(){
        //if the array is null or empty for some reason, just
        //return the default material. if this happens, it is a bug
        if(materialArray == null || materialArray.length == 0)
            return defaultMaterial;

        //if the currentIndex is outside the
        //array's bounds, make it wrap around.
        if(materialArray.length <= currentIndex)
            currentIndex = 0;

        //return the current material
        return materialArray[currentIndex++];
    }

    public void reShuffle() {
        //don't do anything if it's null or the length is 0.
        //if this happens, it is a bug.
        if(materialArray == null || materialArray.length == 0)
            return;

        //if the length is equal to 1, then there is nothing to shuffle.
        if(materialArray.length == 1)
            return;

        //convert to an ArrayList so that collections.shuffle can be used
        ArrayList<Material> tempList = new ArrayList<>(Arrays.asList(materialArray));
        Collections.shuffle(tempList);

        //convert the list back into the array
        for(int i = 0; i < materialArray.length; i++){
            materialArray[i] = tempList.get(i);
        }
    }
}
