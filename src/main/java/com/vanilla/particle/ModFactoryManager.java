package com.vanilla.particle;

import net.minecraft.particle.SimpleParticleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModFactoryManager {
    private static final Map<SimpleParticleType, ModParticleFactory> factories = new HashMap<>();
    private static final List<SimpleParticleType> factoriesHandle =  new ArrayList<>();

    public static void addFactory(SimpleParticleType sp,ModParticleFactory factory){
        factoriesHandle.add(sp);
        factories.put(sp, factory);
    }

    public static ModParticleFactory getFactory(SimpleParticleType sp){
        return factories.get(sp);
    }
}
