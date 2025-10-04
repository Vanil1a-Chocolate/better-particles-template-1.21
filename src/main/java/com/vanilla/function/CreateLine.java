package com.vanilla.function;

import com.vanilla.obj.Point;
import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CreateLine extends CreateFunction {
    private  Vec3d start;
    private  Vec3d end;
    private final ParticleData data;
    private final int precision;

    public CreateLine(Vec3d start, Vec3d end, ParticleData data, int precision){
        super(data.getParticleType());
        this.start = start;
        this.end = end;
        this.data = data;
        this.precision = precision;
    }

    public CreateLine(ParticleData data, int precision){
        super(data.getParticleType());
        this.data = data;
        this.precision = precision;
    }

    public CreateLine(int precision){
        this(new ParticleData(), precision);
    }

    @Override
    protected void work(World world) {
        Point point =  Point.INSTANCE;
        if(point == null){
            return;
        }
        start = point.getStart();
        end = point.getEnd();
        ModParticleManager particleManager = ModParticleManager.getInstance();
        particleManager.cleanWarnParticle();
        String handle = "LINE_"+ particleManager.outGetCurrentHandle();
        for(int i =0;i<precision;i++){
            double t = (double) i / (precision - 1);
            Vec3d p = start.lerp(end, t);
            data.setPosition(p);
            particleManager.addParticle(ModParticle.SPARKLE_PARTICLE,data,world,handle);
        }
        Point.INSTANCE = null;
    }
}
