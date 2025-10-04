package com.vanilla.function;

import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ParticleData;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateSingleParticle extends CreateFunction{
    private final ParticleData data = new ParticleData();
    private SimpleParticleType particleType = ModParticle.SPARKLE_PARTICLE;
    private String group = "SINGLE_";

    public CreateSingleParticle(Vec3d pos, SimpleParticleType particle,String group) {
        super(particle);
        data.setPosition(pos);
        particleType = particle;
        this.group = group;
    }

    public CreateSingleParticle(Vec3d pos) {
        super(new ParticleData().getParticleType());
        data.setPosition(pos);
    }

    @Override
    protected void work(World world) {
        ModParticleManager manager = ModParticleManager.getInstance();
        String handle = group + manager.outGetCurrentHandle();
        manager.addParticle(particleType,data,world,handle);
    }
}
