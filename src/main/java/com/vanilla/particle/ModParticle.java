package com.vanilla.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class ModParticle extends SpriteBillboardParticle {

    private ParticleTextureSheet sheet;
    public final ParticleData data;

    protected ModParticle(ClientWorld clientWorld, double x, double y, double z,double vx ,double vy ,double vz,SpriteProvider sprites,int life) {
        super(clientWorld,x,y,z,vx,vy,vz);
        data = new ParticleData(new Vec3d(x,y,z),new Vec3d(vx,vy,vz),new Color(0,0,0,0));
        data.setLifeTime(life);
        data.setSpriteProvider(sprites);
        setSprite(sprites);
        this.maxAge = life;
        this.scale = 1;
        this.alpha = data.getColor().getAlpha();
    }

    protected ModParticle(ClientWorld clientWorld,ParticleData data){
        super(clientWorld,data.getPosition().getX(),data.getPosition().getY(),data.getPosition().getZ(),
                data.getVelocity().getX(),data.getVelocity().getY(),data.getVelocity().getZ());
        setSprite(data.getSpriteProvider());
        this.data = data;
        this.maxAge = data.getLifeTime();
        this.scale = data.getScale();
        this.sheet = data.getSheet();
        this.alpha = data.getColor().getAlpha();
    }

    @Override
    public ParticleTextureSheet getType() {
        if (sheet == null) {
            return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
        }
        return sheet;
    }


    @Override
    public void tick() {
        super.tick();
        if(data != null&& !data.isMoved()) {
            this.velocityX = 0;
            this.velocityY = 0;
            this.velocityZ = 0;
        }
        if (data != null) {
            this.velocityX = data.getVelocity().getX();
            this.velocityY = data.getVelocity().getY();
            this.velocityZ = data.getVelocity().getZ();
        }
        if (age++ >= maxAge) this.markDead();
    }
}
