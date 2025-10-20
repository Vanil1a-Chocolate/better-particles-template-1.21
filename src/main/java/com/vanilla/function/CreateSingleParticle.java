package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.client.ParticleDataBufferHelper;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.particle.ParticleData;
import com.vanilla.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CreateSingleParticle implements CreateInter{
    private ParticleData data = ModParticleManager.getInstance().getCurrentParticleData();
    private String group = "SINGLE_";

    public static final  CreateSingleParticle INSTANCE = new CreateSingleParticle(Vec3d.ZERO);

    public CreateSingleParticle(ParticleData data,String group) {
        this.data = data;
        this.group = group;
    }



    public CreateSingleParticle(Vec3d pos) {
        data.setPosition(pos);
        if(UseCommandData.isMoved){
            Vec3d yAxis = new Vec3d(1, 0, 0);
            Rotate.rotateAnyAxis(data,UseCommandData.center,yAxis,0.5);
        }
    }

    public CreateSingleParticle(ParticleData data,Vec3d pos) {
        data.setPosition(pos);
        if(UseCommandData.isMoved){
            Vec3d yAxis = new Vec3d(1, 0, 0);
            Rotate.rotateAnyAxis(data,UseCommandData.center,yAxis,0.5);
        }
    }

    public static void CreateTickChangeSingleParticle(Vec3d pos) {
        CreateSingleParticle cr= new CreateSingleParticle(pos);
        cr.getData().setVelocity(new Vec3d(1.5, 0, 0));
        cr.getData().setLifeTime(-1);
        cr.getData().setWork((var_1,var_2)->{
            if(var_2>0){
                Vec3d p = var_1.getPosition();
                CreateSingleParticle c = new CreateSingleParticle(p);
                c.generate(MinecraftClient.getInstance().world);
                return 0;
            }
            return var_2;
        });
        cr.generate(MinecraftClient.getInstance().world);
    }

    public static void CreateSingleParticleByList(){
        List<Vec3d> list = UseCommandData.getPosition();
        List<Vec3d> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Vec3d start = list.get(i);
            Vec3d end   = list.get((i + 1) % list.size());
            result.add(end.subtract(start).normalize());
        }

        CreateSingleParticle cr= new CreateSingleParticle(list.getFirst());
        cr.getData().setWork(((var_1, count) -> {
            int i = count/40;
            if (i>result.size()-1){
                count = 0;
                i=0;
            }
            var_1.setVelocity(result.get(i));
            CreateSingleParticle c = new CreateSingleParticle(var_1.getPosition());
            c.generate(MinecraftClient.getInstance().world);
            return count;
        }));
        cr.getData().setLifeTime(-1);
        cr.generate(MinecraftClient.getInstance().world);
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

    public ParticleData getData() {
        return data;
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

    @Override
    public byte getId(){
        return (byte)SoulGraphPen.ParticleMode.CREATE_SINGLE_PARTICLE.getValue();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVec3d(data.getPosition());
        ParticleDataBufferHelper.write(buf,data);
    }

    public CreateInter read(PacketByteBuf buf) {
        Vec3d pos = buf.readVec3d();
        ParticleData data = ParticleDataBufferHelper.read(buf);
        return new CreateSingleParticle(data,pos);
    }
}
