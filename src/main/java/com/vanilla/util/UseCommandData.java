package com.vanilla.util;

import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class UseCommandData {
    public static Vec3d position;
    public static boolean isMoved = false;

    public static void getPositionFromPicked(){
        List<String> handle = ModParticleManager.getInstance().getWarningParticlesHandle();
        String new_handle = handle.getLast();
        ModParticle particle = ParticleVisionLocator.getReVisionMap().get(new_handle);
        position = particle.data.getPosition();
    }
}
