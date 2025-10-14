package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.particle.ParticleData;
import com.vanilla.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateSingleParticle implements CreateInter{
    private ParticleData data = ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA.copy();
    private String group = "SINGLE_";

    public static final  CreateSingleParticle INSTANCE = new CreateSingleParticle(Vec3d.ZERO);

    public CreateSingleParticle(ParticleData data,String group) {
        this.data = data;
        this.group = group;
    }

    public CreateSingleParticle(Vec3d pos) {
        data.setPosition(pos);
        if(UseCommandData.isMoved){
            data.setMove(() -> {
                data.setPosition(data.getPosition().add(0.05, 0, 0));
                return data.getPosition();
            });
        }
    }

    @Override
    public void generate(World world) {
        ModParticleManager manager = ModParticleManager.getInstance();
        String handle = group + manager.outGetCurrentHandle();
        if(data.getParticleType()!= ModParticleRegister.PREVIEW_PARTICLE){
            if (SoulGraphPen.isSaved){
                SaveJsonToText.getInstance().saveToTextFile(toJson(data));
            }
        }
        manager.addParticle(data,world,handle);
    }

    @Override
    public JsonObject toJson(ParticleData data) {
        JsonObject json = new JsonObject();
        json.addProperty("mode",SoulGraphPen.ParticleMode.CREATE_SINGLE_PARTICLE.getValue());
        Vec3d nPos = DistanceHelper.getDistanceFromTwoVec3d(SaveJsonToText.getInstance().getStartPosition(),data.getPosition());
        JsonObject pos = JsonHelper.UseVec3dToJson(nPos);
        json.add("position", pos);
        json.add("data",ParticleData.DataToJson(data));
        return json;
    }

    @Override
    public void toData(JsonObject json) {
        Vec3d pos = JsonHelper.getVec3dFromJsonEz(json);
        Vec3d NewPos = DistanceHelper.getDistanceFromTwoVec3d(ReadTextToJson.getStartPos(),pos);
        CreateSingleParticle particle = new CreateSingleParticle(NewPos);
        particle.generate(MinecraftClient.getInstance().world);
    }


}
