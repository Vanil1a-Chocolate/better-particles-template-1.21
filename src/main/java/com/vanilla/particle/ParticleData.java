package com.vanilla.particle;

import com.google.gson.JsonObject;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ParticleData {
    private  Vec3d position ;
    private  Vec3d velocity;
    private  Color color;
    private  int lifeTime;
    private SimpleParticleType particleType;
    private boolean isMoved;
    private float scale;
    private ParticleTextureSheet sheet;
    private ModParticleMove move;
    private SpriteProvider spriteProvider;
    public ParticleData(Vec3d velocity, Color color, int lifeTime, SimpleParticleType particleType, boolean isMoved, float scale,ParticleTextureSheet sheet) {
        this.velocity = velocity;
        this.color = color;
        this.lifeTime = lifeTime;
        this.particleType = particleType;
        this.isMoved = isMoved;
        this.scale = scale;
        this.sheet = sheet;
    }

    public ParticleData(){
        lifeTime = 5000;
        position = new Vec3d(0,0,0);
        velocity = new Vec3d(0,0,0);
        color = new Color(0,0,0,0);
        particleType  = ModParticleRegister.SPARKLE_PARTICLE;
    }

    public static JsonObject DataToJson(ParticleData data) {
        JsonObject json = new JsonObject();
        JsonObject color = new JsonObject();
        color.addProperty("red", data.color.getRed());
        color.addProperty("green", data.color.getGreen());
        color.addProperty("blue", data.color.getBlue());
        color.addProperty("alpha", data.color.getAlpha());
        json.addProperty("lifeTime", data.getLifeTime());
        json.addProperty("isMoved", data.isMoved());
        json.addProperty("scale", data.getScale());
        json.addProperty("color",color.toString());
        return json;
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

    public void setMove(ModParticleMove move) {
        this.move = move;
    }

    public ModParticleMove getMove() {
        return move;
    }

    public Color getColor() {
        return color;
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

    public SimpleParticleType getParticleType() { return particleType; }

    public boolean isMoved() { return isMoved; }

    public float getScale() { return scale; }

    public ParticleTextureSheet getSheet() { return sheet; }

    public ParticleData copy() {
        ParticleData copy = new ParticleData();
        copy.position = this.position;
        copy.velocity = this.velocity;
        copy.color = this.color;
        copy.lifeTime = this.lifeTime;
        copy.spriteProvider = this.spriteProvider;
        copy.scale = this.scale;
        copy.particleType = this.particleType;
        copy.isMoved = this.isMoved;
        return copy;
    }
}
