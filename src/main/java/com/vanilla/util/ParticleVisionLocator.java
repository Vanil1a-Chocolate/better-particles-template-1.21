package com.vanilla.util;

import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class ParticleVisionLocator {
    private static final Map<String,String> visionMap = new ConcurrentHashMap<>();

    public ModParticle getClosestParticleInSight(PlayerEntity player, double maxDistance) {
        if (player == null) return null;

        Vec3d eyePos = player.getEyePos();
        Vec3d lookDir = player.getRotationVec(1.0F);

        ModParticle closestParticle = null;
        double closestDistance = Double.MAX_VALUE;
        List<String> particlesHandle = ModParticleManager.getInstance().getParticlesHandle();
        for (String particleType : particlesHandle) {
            Queue<ModParticle> particles = ModParticleManager.getInstance().getParticlesQueue(particleType);
            if (particles == null || particles.isEmpty()) continue;

            for (ModParticle particle : particles) {
                Vec3d particlePos = particle.data.getPosition();

                if (particle.data.getParticleType() != ModParticleRegister.SPARKLE_PARTICLE) continue;
                if (!isParticleInSight(eyePos, lookDir, particlePos, maxDistance)) {
                    continue;
                }

                Vec3d eyeToParticle = particlePos.subtract(eyePos);
                double projection = eyeToParticle.dotProduct(lookDir);

                if (projection < closestDistance) {
                    closestDistance = projection;
                    closestParticle = particle;
                }
            }
        }
        if (closestParticle != null) {
            addWarningParticleToVisionParticle(closestParticle);
        }
        return closestParticle;
    }


    private boolean isParticleInSight(Vec3d eyePos, Vec3d lookDir, Vec3d particlePos, double maxReach) {
        Vec3d eyeToParticle = particlePos.subtract(eyePos);
        double projection = eyeToParticle.dotProduct(lookDir);

        if (projection < 0 || projection > maxReach) {
            return false;
        }

        double distanceToRay = Math.sqrt(eyeToParticle.lengthSquared() - projection * projection);
        return distanceToRay <= 0.25;
    }

    public void addWarningParticleToVisionParticle(ModParticle particle) {
        String warnHandle = visionMap.get(particle.getHandle());
        if( warnHandle != null ){
            ModParticleManager.getInstance().cleanWarnParticleByHandle(warnHandle);
            visionMap.remove(particle.getHandle());
        }else{
            Vec3d pos = particle.data.getPosition();
            String handle = ModParticleManager.getInstance().addWarnParticle(MinecraftClient.getInstance().world, pos);
            visionMap.put(particle.getHandle(), handle);
        }
    }
}
