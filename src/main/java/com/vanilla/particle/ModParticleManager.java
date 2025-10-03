package com.vanilla.particle;

import com.vanilla.util.SendMessageToPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Environment(EnvType.CLIENT)
public final class ModParticleManager {

    private static final ModParticleManager INSTANCE = new ModParticleManager();

    private final Map<String, Queue<Particle>> pool = new ConcurrentHashMap<>();

    private final List<String> particlesHandle = new ArrayList<>();

    private long currentHandle;

    private ModParticleManager() {
        currentHandle = 0L;
    }

    private long currentHandleGenerate(){
        return ++currentHandle;
    }

    public static ModParticleManager getInstance(){
        return INSTANCE;
    }

    public void track(String group,Particle particle){
        if(!particlesHandle.contains(group)) particlesHandle.add(group);
        pool.computeIfAbsent(group, k -> new ConcurrentLinkedDeque<>()).add(particle);
    }

    public void autoTrack(Particle particle){
        long handle = currentHandleGenerate();
        track(String.valueOf(handle),particle);
        printCurrentHandle();
    }

    public void cleanGroup(String group){
        Queue<Particle> queue = pool.get(group);
        if(queue == null) return;
        while(!queue.isEmpty()){
            Particle particle = queue.poll();
            if(particle != null) particle.markDead();
        }
    }

    public void cleanAllGroup(){
        for(String group : particlesHandle){
            boolean isRemoved = particlesHandle.remove(group);
            if(!isRemoved){
                SendMessageToPlayer.sendMessageToPlayer("清除失败");
                return;
            }
            cleanGroup(group);
        }
    }

    public void undo(){
        int length = particlesHandle.size();
        if(length == 0) return;
        cleanGroup(particlesHandle.get(length-1));
        particlesHandle.remove(length-1);
    }

    public void addParticle(ParticleData data, World world){
        Vec3d p = data.getPosition();
        Vec3d v = data.getVelocity();

    }

    public void printCurrentHandle(){
        for(String group : particlesHandle){
            System.out.println(group);
        }
    }
}
