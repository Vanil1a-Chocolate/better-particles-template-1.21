package com.vanilla.particle;

import com.vanilla.BetterParticles;
import com.vanilla.atlas.ModParticleSheet;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModParticleRegister {

    public static final List<ParticleData> dataList = new ArrayList<>();
    public static final SimpleParticleType SIMPLE_DEFAULT_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType WARING_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType PREVIEW_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType DIY_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType METEOR_PARTICLE = FabricParticleTypes.simple();

    public static final ParticleData WARNING_PARTICLE_DATA = new ParticleData(Vec3d.ZERO, new Color(0,0,0,1),5000,WARING_PARTICLE,false,1, ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData SIMPLE_DEFAULT_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0,0,0,1),5000, SIMPLE_DEFAULT_PARTICLE,false,0.8f,ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData PREVIEW_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0f,0f,0f,0.05f),1, PREVIEW_PARTICLE,false,0.3f,ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData TRANSPARENT_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0f,0f,0f,0),5000, PREVIEW_PARTICLE,false,0.3f,ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData DIY_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0f,0f,0f,0),5000, SIMPLE_DEFAULT_PARTICLE,false,0.3f, ModParticleSheet.DEFAULT_PARTICLE_SHEET);
    public static final ParticleData METEOR_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0,0,0,1),20,METEOR_PARTICLE,false,0.4f, ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static void initParticles(){
        registerParticles("magic", SIMPLE_DEFAULT_PARTICLE);
        registerParticles("warn",WARING_PARTICLE);
        registerParticles("preview",PREVIEW_PARTICLE);
        registerParticles("meteor",METEOR_PARTICLE);
        dataList.add(SIMPLE_DEFAULT_PARTICLE_DATA);
        dataList.add(WARNING_PARTICLE_DATA);
        dataList.add(PREVIEW_PARTICLE_DATA);
        dataList.add(METEOR_PARTICLE_DATA);
        registerParticleFactory();
    }

    private static void registerParticles(String name,SimpleParticleType particle){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(BetterParticles.MOD_ID,name),particle);
    }

    public static void registerParticleFactoryEz(SimpleParticleType particleType){
        ParticleFactoryRegistry.getInstance().register(particleType,(fabricSpriteProvider -> {
            ModParticleFactory factory = new ModParticleFactory(fabricSpriteProvider);
            ModFactoryManager.addFactory(particleType,factory);
            return factory;
        }));
    }

    private static void registerParticleFactory(){
        registerParticleFactoryEz(SIMPLE_DEFAULT_PARTICLE);
        registerParticleFactoryEz(WARING_PARTICLE);
        registerParticleFactoryEz(PREVIEW_PARTICLE);
        registerParticleFactoryEz(DIY_PARTICLE);
        registerParticleFactoryEz(METEOR_PARTICLE);
    }


}
