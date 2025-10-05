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
    private  ParticleData data;

    private boolean flag = true; //true为单步模式自动产生句柄追踪 false为多步模式
    private String handle = null;

    private static  ModParticleFactory INSTANCE ;


    public static ModParticleFactory getInstance() {
        return INSTANCE;
    }

    public static void setModeParticleFactory(ModParticleFactory factory) {
        INSTANCE = factory;
    }

    public static void setModeParticleFactoryEz(SimpleParticleType particle) {
         setModeParticleFactory(ModFactoryManager.getFactory(particle));
    }


    public void reSetFlag(){
        flag = true;
    }

    public ModParticleFactory(SpriteProvider spriteProvide){
        spriteProvider = spriteProvide;
    }


    public void setHandle(String handle){
        this.flag = false;
        this.handle = handle;
    }

    public void setData(ParticleData data){
        this.data = data;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if(data == null) data = ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA;
        data.setSpriteProvider(spriteProvider);
        Particle particle = new ModParticle(world,data);
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
