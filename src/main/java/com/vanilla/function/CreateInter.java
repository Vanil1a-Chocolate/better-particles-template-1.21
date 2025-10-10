package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.particle.ParticleData;
import net.minecraft.world.World;

public interface CreateInter {
    void generate(World world);
    JsonObject toJson(ParticleData data);
}
