package com.vanilla.particle;

import com.google.gson.JsonObject;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.Identifier;
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
    private Identifier id;
    private boolean tickChange;
    private ModParticleWork work;
    public ParticleData(Vec3d velocity, Color color, int lifeTime, SimpleParticleType particleType, boolean isMoved, float scale,ParticleTextureSheet sheet,boolean tickChange,Identifier identifier) {
        this.velocity = velocity;
        this.color = color;
        this.lifeTime = lifeTime;
        this.particleType = particleType;
        this.isMoved = isMoved;
        this.scale = scale;
        this.sheet = sheet;
        this.tickChange = tickChange;
        this.id = identifier;
    }

    public ParticleData(){
        lifeTime = 5000;
        position = new Vec3d(0,0,0);
        velocity = new Vec3d(0,0,0);
        color = new Color(0,0,0,0);
        particleType  = ModParticleRegister.SIMPLE_DEFAULT_PARTICLE;
        tickChange = false;
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setMove(ModParticleMove move) {
        this.move = move;
    }

    public void setWork(ModParticleWork work) {
        this.work = work;
    }
    public ModParticleWork getWork() {
        return work;
    }

    public Identifier getId() {
        return id;
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

    public void setId(Identifier id) { this.id = id; }

    public ParticleTextureSheet getSheet() { return sheet; }

    public boolean isTickChange() { return tickChange; }

    public void  setVelocity(Vec3d velocity) { this.velocity = velocity; }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }


    public ParticleData copy() {
        ParticleData copy = new ParticleData();
        copy.sheet = this.sheet;
        copy.position = this.position;
        copy.velocity = this.velocity;
        copy.color = this.color;
        copy.lifeTime = this.lifeTime;
        copy.spriteProvider = this.spriteProvider;
        copy.scale = this.scale;
        copy.particleType = this.particleType;
        copy.isMoved = this.isMoved;
        copy.tickChange = this.tickChange;
        return copy;
    }
}
