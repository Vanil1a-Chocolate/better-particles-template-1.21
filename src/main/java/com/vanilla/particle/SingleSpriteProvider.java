package com.vanilla.particle;

import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.random.Random;

public class SingleSpriteProvider implements SpriteProvider {
    private final Sprite sprite;
    public SingleSpriteProvider(Sprite s) { this.sprite = s; }
    @Override public Sprite getSprite(int particleAge, int maxAge) { return sprite; }
    @Override public Sprite getSprite(Random random) { return sprite; }
}