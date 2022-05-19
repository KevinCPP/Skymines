package com.cuboidcraft.skymines.util;

import com.google.gson.InstanceCreator;
import java.lang.reflect.Type;
import java.util.EnumMap;

class EnumMapInstanceCreator<K extends Enum<K>, V> implements InstanceCreator<EnumMap<K, V>> {
    private final Class<K> enumclass;

    public EnumMapInstanceCreator(final Class<K> enumc){
        super();
        this.enumclass = enumc;
    }

    @Override
    public EnumMap<K, V> createInstance(final Type type){
        return new EnumMap<K, V>(enumclass);
    }
}