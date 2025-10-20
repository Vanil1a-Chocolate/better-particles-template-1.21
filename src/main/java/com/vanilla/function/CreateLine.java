package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.obj.Point;
import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.particle.ParticleData;
import com.vanilla.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class CreateLine implements CreateInter {
    private final ParticleData data;
    private final int precision;
    private static boolean isVisible = false;
    public static final CreateLine INSTANCE = new CreateLine(0);

    public CreateLine(Vec3d start, Vec3d end, ParticleData data, int precision){
        Point.INSTANCE = new Point(start,end);
        this.data = data;
        this.precision = precision;
    }

    public CreateLine(ParticleData data, int precision){
        this.data = data;
        this.precision = precision;
    }

    public CreateLine(int precision){
        this(ModParticleManager.getInstance().getCurrentParticleData().copy(), precision);
    }

    public static void CreateLineAuto(ParticleData data){
        Point point = Point.INSTANCE;
        if (point == null) return;
        double dist = point.getStart().distanceTo(point.getEnd());
        CreateLine create = new CreateLine(data,(int) dist);
        create.generate(MinecraftClient.getInstance().world);
    }

    public static void CreateLineAuto(Vec3d start,Vec3d end,ParticleData data){
        Point.INSTANCE = new Point(start,end);
        CreateLineAuto(data);
    }


    public static void CreateLineEz(Vec3d start, Vec3d end, ParticleData data, int precision){
        CreateLine createLine = new CreateLine(start, end, data, precision);
        createLine.generate(MinecraftClient.getInstance().world);
    }

    public static void CreateLineEz(Vec3d start, Vec3d end, int precision){
        CreateLineEz(start,end,ModParticleManager.getInstance().getCurrentParticleData().copy(),precision);
    }



    public static void UseVisionParticleCreateLine(boolean mode){
        List<String> handle = ModParticleManager.getInstance().getWarningParticlesHandle();
        Map<String, ModParticle> reVisionMap = ParticleVisionLocator.getReVisionMap();
        isVisible = true;
        for(int i =0;i<handle.size()-1;i++){
            for(int j = i+1;j<handle.size();j++){
                if(mode){
                    CreateLineAuto(reVisionMap.get(handle.get(i)).data.getPosition(),reVisionMap.get(handle.get(j)).data.getPosition(),ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA.copy());
                }else{
                    CreateLineEz(reVisionMap.get(handle.get(i)).data.getPosition(),reVisionMap.get(handle.get(j)).data.getPosition(),100);
                }
            }
        }
        ParticleVisionLocator.cleanAllVisionMap();
        ModParticleManager.getInstance().cleanWarnParticle();
        isVisible = false;
    }

    @Override
    public void generate(World world) {
        Point point =  Point.INSTANCE;
        if(point == null){
            return;
        }
        Vec3d start = point.getStart();
        Vec3d end = point.getEnd();
        ModParticleManager particleManager = ModParticleManager.getInstance();
        if (!isVisible){
            particleManager.cleanWarnParticle();
        }
        if(data.getParticleType()!= ModParticleRegister.PREVIEW_PARTICLE){
            if (SoulGraphPen.isSaved){
                SaveJsonToText.getInstance().saveToTextFile(toJson(data));
            }
        }
        String handle = "LINE_"+ particleManager.outGetCurrentHandle();
        for(int i =0;i<precision;i++){
            double t = (double) i / (precision - 1);
            Vec3d p = start.lerp(end, t);
            data.setPosition(p);
            particleManager.addParticle(data,world,handle);
        }
        Point.INSTANCE = null;
    }

    @Override
    public JsonObject toJson(ParticleData data) {
        JsonObject json = new JsonObject();
        json.addProperty("mode",SoulGraphPen.ParticleMode.CREATE_LINE.getValue());
        Vec3d start = DistanceHelper.getDistanceFromTwoVec3d(SaveJsonToText.getInstance().getStartPosition(), Point.INSTANCE.getStart());
        Vec3d end = DistanceHelper.getDistanceFromTwoVec3d(SaveJsonToText.getInstance().getStartPosition(), Point.INSTANCE.getEnd());
        JsonObject J_start = JsonHelper.UseVec3dToJson(start);
        JsonObject J_end = JsonHelper.UseVec3dToJson(end);
        json.add("start",J_start);
        json.add("end",J_end);
        json.add("data",ParticleData.DataToJson(data));
        return json;
    }

    @Override
    public void toData(JsonObject json) {
        Vec3d J_start = JsonHelper.getVec3dFromJson(json.getAsJsonObject("start"));
        Vec3d J_end = JsonHelper.getVec3dFromJson(json.getAsJsonObject("end"));
        Vec3d start = DistanceHelper.getDistanceFromTwoVec3d(ReadTextToJson.getStartPos(),J_start);
        Vec3d end = DistanceHelper.getDistanceFromTwoVec3d(ReadTextToJson.getStartPos(),J_end);
        CreateLine line = new CreateLine(start,end,ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA.copy(),50);
        line.generate(MinecraftClient.getInstance().world);
    }

    @Override
    public byte getId() {
        return (byte)SoulGraphPen.ParticleMode.CREATE_LINE.getValue();
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public CreateInter read(PacketByteBuf buf) {
        return null;
    }
}
