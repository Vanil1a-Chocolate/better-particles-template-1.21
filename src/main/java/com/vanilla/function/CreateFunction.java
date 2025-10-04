package com.vanilla.function;

import com.vanilla.particle.ModFactoryManager;
import com.vanilla.particle.ModParticleFactory;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.world.World;

public abstract class CreateFunction implements CreateInter{

    public CreateFunction(SimpleParticleType particle) {
        init(particle);
    }

    protected void closeWork(){
        ModParticleFactory.getInstance().reSetFlag();
        ModParticleFactory.setModeParticleFactory(null);
    }

    public void init(SimpleParticleType type){
        if (type == null) return;
        ModParticleFactory.setModeParticleFactory(ModFactoryManager.getFactory(type));
    }
    @Override
    public void generate(World world){
        work(world);
        closeWork();
    }

    protected abstract void work(World world);

}
