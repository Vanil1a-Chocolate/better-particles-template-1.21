package com.vanilla.atlas;

import com.vanilla.BetterParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class AtlasProvider {
    public AtlasProvider() {
        Path path = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("assets")
                .resolve(BetterParticles.MOD_ID)
                .resolve("textures")
                .resolve("atlas").resolve(BetterParticles.MOD_ID+"_atlas.png");
        int id = uploadAtlasToTexture(path);
        AtlasSpriteManager.getInstance().setAtlasGlTextureId(id);
    }

    private int uploadAtlasToTexture(@NotNull Path path) {
        try{
            NativeImage nativeImage = NativeImage.read(Files.newInputStream(path));
            NativeImageBackedTexture atlasTexture = new NativeImageBackedTexture(nativeImage);
            TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
            Identifier ATLAS_ID = Identifier.of(BetterParticles.MOD_ID+"_atlas.png");
            textureManager.registerTexture(ATLAS_ID, atlasTexture);

            int GlId =atlasTexture.getGlId();

            AtlasSpriteManager.getInstance().setAtlasGlTextureId(GlId);
            AtlasSpriteManager.getInstance().setAtlasSpriteId(ATLAS_ID);
            BetterParticles.LOGGER.info("图集已注册：{}，GPU ID：{}", ATLAS_ID, GlId);
            return GlId;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
