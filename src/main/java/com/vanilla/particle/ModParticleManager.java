package com.vanilla.particle;

import com.vanilla.util.SendMessageToPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Environment(EnvType.CLIENT)
public final class ModParticleManager {

    private static final ModParticleManager INSTANCE = new ModParticleManager();

    private final Map<String, Queue<ModParticle>> pool = new ConcurrentHashMap<>();

    private final List<String> particlesHandle = new ArrayList<>();
    private final List<String> warningParticlesHandle = new ArrayList<>();

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

    public List<String> getParticlesHandle(){
        return particlesHandle;
    }
    public long outGetCurrentHandle(){
        return currentHandleGenerate();
    }

    public Queue<ModParticle> getParticlesQueue(String handle){
        return pool.get(handle);
    }

    public void track(String group,ModParticle particle){
        particle.setHandle(group);
        if(!particlesHandle.contains(group)) particlesHandle.add(group);
        pool.computeIfAbsent(group, k -> new ConcurrentLinkedDeque<>()).add(particle);
    }

    public void autoTrack(ModParticle particle){
        long handle = currentHandleGenerate();
        track(String.valueOf(handle),particle);
        printCurrentSingleHandle();
    }

    public void cleanGroup(String group){
        Queue<ModParticle> queue = pool.get(group);
        particlesHandle.remove(group);
        if(queue == null) return;
        while(!queue.isEmpty()){
            Particle particle = queue.poll();
            if(particle != null) particle.markDead();
        }
    }

    public void cleanAllGroup(){
        currentHandle = 0L;
        for (int i = particlesHandle.size() - 1; i >= 0; i--){
            String group  = particlesHandle.remove(i);
            cleanGroup(group);
        }
    }

    public boolean undo(){
        int length = particlesHandle.size();
        if(length == 0) return false;
        cleanGroup(particlesHandle.get(length-1));
        return true;
    }

    private void addParticle(SimpleParticleType particle, ParticleData data, World world){
        Vec3d p = data.getPosition();
        Vec3d v = data.getVelocity();
        world.addParticle(particle,p.x,p.y,p.z,v.x,v.y,v.z);
        ModParticleFactory.getInstance().reSetFlag();
        ModParticleFactory.setModeParticleFactory(null);
    }

    public void addParticle( ParticleData data, World world,String handle){
        ModParticleFactory.setModeParticleFactoryEz(data.getParticleType());
        ModParticleFactory factory = ModParticleFactory.getInstance();
        factory.setData(data);
        factory.setHandle(handle);
        addParticle(data.getParticleType(),data,world);
    }

    public void addParticle(ParticleData data, World world){
        addParticle(data,world,null);
    }



    public String addWarnParticle(World world, Vec3d p){
        String handle = "WARN_"+ currentHandleGenerate();
        warningParticlesHandle.add(handle);
        ModParticleRegister.WARNING_PARTICLE_DATA.setPosition(p);
        addParticle(ModParticleRegister.WARNING_PARTICLE_DATA,world,handle);
        return handle;
    }

    public void cleanWarnParticle(){
        for(String handle : warningParticlesHandle){
            cleanGroup(handle);
        }
        warningParticlesHandle.clear();
    }

    public void cleanWarnParticleByHandle(String handle){
        cleanGroup(handle);
        warningParticlesHandle.remove(handle);
    }

    public void printCurrentHandle(){
        for(String group : particlesHandle){
            SendMessageToPlayer.sendMessageToPlayer(group);
        }
    }

    public void printCurrentSingleHandle(){
        SendMessageToPlayer.sendMessageToPlayer("当前自动产生句柄为:"+ currentHandle);
    }

    public List<String> getWarningParticlesHandle(){
        return warningParticlesHandle;
    }

}
