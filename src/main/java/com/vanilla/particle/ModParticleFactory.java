package com.vanilla.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ModParticleFactory implements ParticleFactory<SimpleParticleType> {
    private final SpriteProvider spriteProvider;
    private final ParticleData data;

    public ModParticleFactory(ParticleData data, SpriteProvider spriteProvider) {
        this.data = data;
        this.spriteProvider = spriteProvider;
    }

    public ModParticleFactory(SpriteProvider spriteProvide){
        this(new ParticleData(), spriteProvide);
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        Particle particle = new ModParticle(world,x,y,z,velocityX,velocityY,velocityZ,spriteProvider, data.getLifeTime());
        ModParticleManager.getInstance().autoTrack(particle);
        return particle ;
    }
}
