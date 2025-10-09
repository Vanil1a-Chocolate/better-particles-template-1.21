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
    private  double pitchDeg ;
    private  double yawDeg;
    public static double commandPitchDeg = 90;


    public CreateCircle(double radius, Vec3d position, ParticleData data, int precision) {
        this(radius,position,data,precision,commandPitchDeg,0);
    }
    public CreateCircle(double radius, Vec3d position, int precision) {
        this(radius,position,ModParticleRegister.SIMPLE_DEFAULT_PARTICLE_DATA,precision);
    }

    public CreateCircle(double radius, Vec3d position, ParticleData data,int precision, double pitchDeg, double yawDeg) {
        this.radius = radius;
        this.position = position;
        this.data = data;
        this.precision = precision;
        this.pitchDeg = pitchDeg;
        this.yawDeg = yawDeg;
    }
    @Override
    public void generate(World world) {
        double pitch = Math.toRadians(pitchDeg);
        double yaw = Math.toRadians(yawDeg);

        double nx = Math.cos(pitch) * Math.sin(yaw);
        double ny = Math.sin(pitch);
        double nz = Math.cos(pitch) * Math.cos(yaw);
        Vec3d normal = new Vec3d(nx, ny, nz).normalize();

        Vec3d u;

        if (Math.abs(normal.x) < Math.abs(normal.y) && Math.abs(normal.x) < Math.abs(normal.z)) {
            u = new Vec3d(1, 0, 0);
        } else if (Math.abs(normal.y) < Math.abs(normal.z)) {
            u = new Vec3d(0, 1, 0);
        } else {
            u = new Vec3d(0, 0, 1);
        }
        u = normal.crossProduct(u).normalize();

        Vec3d v = normal.crossProduct(u).normalize();

        ModParticleManager particleManager = ModParticleManager.getInstance();
        String handle = "CIRCLE_"+ particleManager.outGetCurrentHandle();
        for(int i =0;i<precision;i++){
            ParticleData data_new = data.copy();
            double t = 2*Math.PI*i/precision;
            Vec3d point = position.add(u.multiply(Math.cos(t)*radius))
                                  .add(v.multiply(Math.sin(t)*radius));
            data_new.setPosition(point);
            particleManager.addParticle(data_new,world,handle);
        }
    }
}
