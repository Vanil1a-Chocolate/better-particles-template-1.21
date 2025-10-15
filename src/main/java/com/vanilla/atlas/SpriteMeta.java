package com.vanilla.atlas;

import net.minecraft.util.Identifier;

public class SpriteMeta {
    private final Identifier textureId; // 小纹理的唯一标识（作为Manager的Key）
    private final float uMin, uMax;     // UV坐标（预计算好）
    private final float vMin, vMax;     // UV坐标
    private final int width, height;    // 小纹理宽高
    private final int xInAtlas;
    private final int yInAtlas;

    public SpriteMeta(
        Identifier textureId, 
        float uMin, float uMax, 
        float vMin, float vMax, 
        int width, int height,
        int xInAtlas, int yInAtlas
    ) {
        this.textureId = textureId;
        this.uMin = uMin;
        this.uMax = uMax;
        this.vMin = vMin;
        this.vMax = vMax;
        this.width = width;
        this.height = height;
        this.xInAtlas = xInAtlas;
        this.yInAtlas = yInAtlas;
    }

    // Getter方法（仅暴露读取，不允许修改，保证元数据不可变）
    public Identifier getTextureId() { return textureId; }
    public float geMinU() { return uMin; }
    public float getMaxU() { return uMax; }
    public float getMinV() { return vMin; }
    public float getMaxV() { return vMax; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getXInAtlas() { return xInAtlas; }
    public int getYInAtlas() { return yInAtlas; }
}