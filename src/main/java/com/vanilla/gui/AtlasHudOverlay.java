package com.vanilla.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.vanilla.item.SoulGraphPen;
import com.vanilla.util.UseCommandData;
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

        testSprite = UseCommandData.getSprite();
        if (client.player != null && (client.world == null || testSprite == null|| !client.player.getMainHandStack().isOf(SoulGraphPen.getInstance()))) {
            return;
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