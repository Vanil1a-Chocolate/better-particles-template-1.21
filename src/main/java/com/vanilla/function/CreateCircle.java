package com.vanilla.function;

import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateCircle extends CreateFunction {
    private double radius;
    private Vec3d position;
    private ParticleData data;
    private int precision;

    public CreateCircle(double radius, Vec3d position, ParticleData data, int precision) {
        this.radius = radius;
        this.position = position;
        this.data = data;
        this.precision = precision;
    }
    public CreateCircle(double radius, Vec3d position, int precision) {
        this.radius = radius;
        this.position = position;
        this.data = new ParticleData();
        this.precision = precision;
    }

    @Override
    protected void work(World world) {
        double allR = 360.0;
        double eachR = allR / precision;
        ModParticleManager particleManager = ModParticleManager.getInstance();
        String handle = "CIRCLE"+ particleManager.outGetCurrentHandle();
        for(int i =0;i<precision;i++){
            double x = position.x + radius * Math.cos(Math.toRadians(i*eachR));
            double z = position.z + radius * Math.sin(Math.toRadians(i*eachR));
            Vec3d vec = new Vec3d(x, position.y, z);
            data.setPosition(vec);
            particleManager.addParticle(ModParticle.SPARKLE_PARTICLE,data,world,handle);
        }
    }
}
