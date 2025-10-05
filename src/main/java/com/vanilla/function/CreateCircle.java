package com.vanilla.function;

import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import com.vanilla.particle.ParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateCircle implements CreateInter {
    private final double radius;
    private final Vec3d position;
    private final ParticleData data;
    private final int precision;

    public CreateCircle(double radius, Vec3d position, ParticleData data, int precision) {
        this.radius = radius;
        this.position = position;
        this.data = data;
        this.precision = precision;
    }
    public CreateCircle(double radius, Vec3d position, int precision) {
        this.radius = radius;
        this.position = position;
        this.data = ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA;
        this.precision = precision;
    }

    @Override
    public void generate(World world) {
        double allR = 360.0;
        double eachR = allR / precision;
        ModParticleManager particleManager = ModParticleManager.getInstance();
        String handle = "CIRCLE_"+ particleManager.outGetCurrentHandle();
        for(int i =0;i<precision;i++){
            double x = position.x + radius * Math.cos(Math.toRadians(i*eachR));
            double z = position.z + radius * Math.sin(Math.toRadians(i*eachR));
            Vec3d vec = new Vec3d(x, position.y, z);
            data.setPosition(vec);
            particleManager.addParticle(data,world,handle);
        }
    }
}
