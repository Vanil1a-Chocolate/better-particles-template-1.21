package com.vanilla.function;

import com.vanilla.particle.ModParticleFactory;
import net.minecraft.world.World;

public abstract class CreateFunction implements CreateInter{
    protected void closeWork(){
        ModParticleFactory.getInstance().reSetFlag();
    }

    @Override
    public void generate(World world){
        work(world);
        closeWork();
    }

    protected abstract void work(World world);
}
