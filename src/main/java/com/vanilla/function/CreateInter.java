package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.obj.GenerateResult;
import com.vanilla.obj.GenerateResultCustom;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ParticleData;
import net.minecraft.network.PacketByteBuf;

import java.util.List;

public interface CreateInter {
    GenerateResultCustom generate();
    JsonObject toJson(ParticleData data);
    void toData(JsonObject json);
    byte getId();
    void write(PacketByteBuf buf);
    CreateInter read(PacketByteBuf buf);
    ParticleData getData();
    default void clientGenerate(){
        GenerateResultCustom result = generate();
        if(result==null) return;
        clientGenerate(result);
    }

    default void clientGenerate(GenerateResultCustom custom){
        List<GenerateResult> result_list = custom.results();
        for(GenerateResult result : result_list){
            ModParticleManager.getInstance().addParticle(result.data(),result.handle());
        }
    }
    default void serverGenerate(){
        GenerateResultCustom result = generate();
        if(result==null) return;
        ModParticleManager.getInstance().addParticleAtServer(result);
    }
}
