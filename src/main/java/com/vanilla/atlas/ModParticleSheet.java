package com.vanilla.atlas;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;

public class ModParticleSheet implements ParticleTextureSheet {
    public static final ModParticleSheet DEFAULT_PARTICLE_SHEET = new ModParticleSheet();
    private ModParticleSheet() {}
        @Override
        public BufferBuilder begin(Tessellator tessellator, TextureManager textureManager) {
            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0,AtlasSpriteManager.getInstance().getAtlasSpriteId());
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            return tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }
}
