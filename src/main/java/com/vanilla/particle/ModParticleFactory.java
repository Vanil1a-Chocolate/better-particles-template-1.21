package com.vanilla.particle;

import com.vanilla.BetterParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ModParticleFactory implements ParticleFactory<SimpleParticleType>{
    private final SpriteProvider spriteProvider;
    private final ParticleData data;

    private boolean flag = true; //true为单步模式自动产生句柄追踪 false为多步模式
    private String handle = null;

    private static  ModParticleFactory INSTANCE ;


    public static ModParticleFactory getInstance() {
        return INSTANCE;
    }

    public static void setModeParticleFactory(ModParticleFactory factory) {
        INSTANCE = factory;
    }

    public ModParticleFactory(ParticleData data, SpriteProvider spriteProvider) {
        this.data = data;
        this.spriteProvider = spriteProvider;
    }

    public void reSetFlag(){
        flag = true;
    }

    public ModParticleFactory(SpriteProvider spriteProvide){
        this(new ParticleData(), spriteProvide);

    }

    public SpriteProvider getSpriteProvider(){
        return spriteProvider;
    }

    public void setHandle(String handle){
        this.flag = false;
        this.handle = handle;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        Particle particle = new ModParticle(world,x,y,z,velocityX,velocityY,velocityZ,spriteProvider, data.getLifeTime());
        if(this != INSTANCE) {
            BetterParticles.LOGGER.atError().log("严重错误！当前Factory与实例INSTANCE不符");
        }
        if(flag){
            ModParticleManager.getInstance().autoTrack(particle);
        }else{
            if(handle == null){
                ModParticleManager.getInstance().autoTrack(particle);
            }else{
                ModParticleManager.getInstance().track(handle, particle);
            }
        }
        return particle ;
    }
}
