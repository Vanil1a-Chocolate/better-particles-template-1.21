package com.vanilla.util;

import com.vanilla.item.SoulGraphPen;
import com.vanilla.particle.ModParticle;
import com.vanilla.particle.ModParticleManager;
import com.vanilla.particle.ModParticleRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class ParticleVisionLocator {
    private static final Map<String,String> visionMap = new ConcurrentHashMap<>();
    private static final Map<String,ModParticle> reVisionMap = new ConcurrentHashMap<>();

    public ModParticle getClosestParticleInSight(PlayerEntity player, double maxDistance, SoulGraphPen.ParticleMode mode) {
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

                if (particle.data.getParticleType() != ModParticleRegister.SIMPLE_DEFAULT_PARTICLE) continue;
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
            if(mode == SoulGraphPen.ParticleMode.CREATE_SINGLE_PARTICLE){
                addWarningParticleToVisionParticle(closestParticle);
            }else if (mode ==SoulGraphPen.ParticleMode.CREATE_CIRCLE){
                if (closestParticle.getHandle().startsWith("CIRCLE_")) {
                    addWarningParticleToVisionParticle(Objects.requireNonNull(ModParticleManager.getInstance().getParticlesQueue(closestParticle.getHandle() + "_CENTER").peek()));
                }
            }
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
        if(!particle.getHandle().startsWith("SINGLE_")){
            particle.setHandle("SINGLE_"+particle.getHandle()+"_"+ModParticleManager.getInstance().outGetCurrentHandle());
        }
        String warnHandle = visionMap.get(particle.getHandle());
        if( warnHandle != null ){
            ModParticleManager.getInstance().cleanWarnParticleByHandle(warnHandle);
            visionMap.remove(particle.getHandle());
            reVisionMap.remove(warnHandle);
        }else{
            Vec3d pos = particle.data.getPosition();
            String handle = ModParticleManager.getInstance().addWarnParticle(MinecraftClient.getInstance().world, pos);
            visionMap.put(particle.getHandle(), handle);
            reVisionMap.put(handle, particle);
        }
    }

    public static void cleanAllVisionMap(){
        visionMap.clear();
        reVisionMap.clear();
    }

    public static Map<String,ModParticle> getReVisionMap(){
        return reVisionMap;
    }
}
