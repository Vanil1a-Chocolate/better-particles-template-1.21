package com.vanilla.particle;

import com.vanilla.BetterParticles;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticle extends SpriteBillboardParticle {


    public static final SimpleParticleType SPARKLE_PARTICLE = FabricParticleTypes.simple();
    public static void initParticles(){
        registerParticles("magic",SPARKLE_PARTICLE);
        ParticleFactoryRegistry.getInstance().register(SPARKLE_PARTICLE,ModParticleFactory::new);
    }

    private static void registerParticles(String name,SimpleParticleType particle){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(BetterParticles.MOD_ID,name),particle);
    }

    protected ModParticle(ClientWorld clientWorld, double x, double y, double z,double vx ,double vy ,double vz,SpriteProvider sprites,int life) {
        super(clientWorld,x,y,z,vx,vy,vz);
        setSprite(sprites);
        this.maxAge = life;
    }

    protected ModParticle(ClientWorld clientWorld,double x, double y, double z,SpriteProvider sprites,int life) {
        super(clientWorld,x,y,z);
        setSprite(sprites);
        this.maxAge = life;
    }

    protected ModParticle(ClientWorld clientWorld,ParticleData data) {
        this(clientWorld,data.getPosition().x,data.getPosition().y,data.getPosition().z
        ,data.getVelocity().x,data.getVelocity().y,data.getVelocity().z, data.getSpriteProvider(), data.getLifeTime());
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Override
    public void tick() {
        super.tick();
        if (age++ >= maxAge) this.markDead();
    }
}
