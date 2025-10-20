package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.particle.ParticleData;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;

public interface CreateInter {
    void generate(World world);
    JsonObject toJson(ParticleData data);
    void toData(JsonObject json);
    byte getId();
    void write(PacketByteBuf buf);
    CreateInter read(PacketByteBuf buf);
}
