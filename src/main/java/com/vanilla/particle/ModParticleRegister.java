package com.vanilla.particle;

import com.vanilla.BetterParticles;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ModParticleRegister {
    public static final SimpleParticleType SPARKLE_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType WARING_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType PREVIEW_PARTICLE = FabricParticleTypes.simple();


    public static final ParticleData WARNING_PARTICLE_DATA = new ParticleData(Vec3d.ZERO, new Color(0,0,0,1),5000,WARING_PARTICLE,false,1, ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData SIMPLE_DEFAULT_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0,0,0,1),5000,SPARKLE_PARTICLE,false,0.8f,ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData PREVIEW_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0f,0f,0f,0.05f),1, PREVIEW_PARTICLE,false,0.3f,ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);
    public static final ParticleData TRANSPARENT_PARTICLE_DATA = new ParticleData(Vec3d.ZERO,new Color(0f,0f,0f,0),5000, PREVIEW_PARTICLE,false,0.3f,ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT);

    public static void initParticles(){
        registerParticles("magic",SPARKLE_PARTICLE);
        registerParticles("warn",WARING_PARTICLE);
        registerParticles("preview",PREVIEW_PARTICLE);

        registerParticleFactory();
    }

    private static void registerParticles(String name,SimpleParticleType particle){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(BetterParticles.MOD_ID,name),particle);
    }

    private static void registerParticleFactory(){
        ParticleFactoryRegistry.getInstance().register(SPARKLE_PARTICLE,(fabricSpriteProvider -> {
            ModParticleFactory factory = new ModParticleFactory(fabricSpriteProvider);
            ModFactoryManager.addFactory(SPARKLE_PARTICLE,factory);
            return factory;
        }));
        ParticleFactoryRegistry.getInstance().register(WARING_PARTICLE,(fabricSpriteProvider -> {
            ModParticleFactory factory = new ModParticleFactory(fabricSpriteProvider);
            ModFactoryManager.addFactory(WARING_PARTICLE,factory);
            return factory;
        }));
        ParticleFactoryRegistry.getInstance().register(PREVIEW_PARTICLE,(fabricSpriteProvider -> {
            ModParticleFactory factory = new ModParticleFactory(fabricSpriteProvider);
            ModFactoryManager.addFactory(PREVIEW_PARTICLE,factory);
            return factory;
        }));
    }


}
