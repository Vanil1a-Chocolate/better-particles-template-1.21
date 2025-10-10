package com.vanilla.function;

import com.google.gson.JsonObject;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.particle.ParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateSingleParticle implements CreateInter{
    private ParticleData data = ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA.copy();
    private String group = "SINGLE_";

    public CreateSingleParticle(ParticleData data,String group) {
        this.data = data;
        this.group = group;
    }

    public CreateSingleParticle(Vec3d pos) {
        data.setPosition(pos);
    }

    @Override
    public void generate(World world) {
        ModParticleManager manager = ModParticleManager.getInstance();
        String handle = group + manager.outGetCurrentHandle();
        manager.addParticle(data,world,handle);
    }

    @Override
    public JsonObject toJson(ParticleData data) {
        return null;
    }
}
