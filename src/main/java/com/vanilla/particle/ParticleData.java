package com.vanilla.particle;

import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ParticleData {
    private  Vec3d position;
    private  Vec3d velocity;
    private  Color color;
    private  int lifeTime;

    private SpriteProvider spriteProvider;

    public ParticleData(Vec3d position, Vec3d velocity, Color color) {
        this.position = position;
        this.velocity = velocity;
        this.color = color;
    }

    public ParticleData(){
        lifeTime = 5000;
        position = new Vec3d(0,0,0);
        velocity = new Vec3d(0,0,0);
        color = new Color(0,0,0,0);
    }

    public Vec3d getPosition() {
        return position;
    }

    public void setPosition(Vec3d position) {
        this.position = position;
    }

    public Vec3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public SpriteProvider getSpriteProvider() {
        return spriteProvider;
    }

    public void setSpriteProvider(SpriteProvider spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
}
