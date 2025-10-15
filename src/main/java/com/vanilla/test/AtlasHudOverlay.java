package com.vanilla.test;

import com.mojang.blaze3d.systems.RenderSystem;
import com.vanilla.BetterParticles;
import com.vanilla.atlas.AtlasSpriteManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

public class AtlasHudOverlay implements HudRenderCallback {

    public static Sprite testSprite;

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        testSprite = AtlasSpriteManager.getInstance().getSprite(Identifier.of(BetterParticles.MOD_ID,"preview.png"));
        if (client.world == null || testSprite == null) {
            return; // 确保 Sprite 已初始化
        }

        Identifier atlasId = testSprite.getAtlasId();
        int spriteWidth = testSprite.getContents().getWidth();
        int spriteHeight = testSprite.getContents().getHeight();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, atlasId); // 绑定图集
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // 不改变颜色
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int screenX = 10;
        int screenY = 10;
        int renderWidth = spriteWidth * 2;
        int renderHeight = spriteHeight * 2;

        drawContext.drawSprite(
                screenX,
                screenY,
                0,
                renderWidth,
                renderHeight,
                testSprite
        );

        RenderSystem.disableBlend();
    }
}