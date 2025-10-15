package com.vanilla.atlas;

import com.vanilla.BetterParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtlasGenerator {
    private static final int MaxWidth = 128;
    private static List<File> getParticleTextureFiles() {
        Path imageDir = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("assets")
                .resolve(BetterParticles.MOD_ID)
                .resolve("textures")
                .resolve("particle");
        if (Files.exists(imageDir) && Files.isDirectory(imageDir)) {
            try(Stream<Path> files = Files.list(imageDir)) {
                return  files
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            }catch (IOException e) {
                return Collections.emptyList();
            }
        }
        return List.of();
    }

    public static void generate(){
        generate(MaxWidth);
    }

    private static void generate(int maxWidth){
        Path atlasDir = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("assets")
                .resolve(BetterParticles.MOD_ID)
                .resolve("textures")
                .resolve("atlas");
        try {
            Files.createDirectories(atlasDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generate(atlasDir, maxWidth);
    }

    public static void generate(Path outputAtlas,int maxWidth){
        List<File> fileList = getParticleTextureFiles();
        BufferedImage first;
        try {
            first = ImageIO.read(fileList.getFirst());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int tileWidth = first.getWidth();
        int tileHeight = first.getHeight();
        int count = fileList.size();

        int cols = Math.min(count, maxWidth / tileWidth);
        int rows = (count + cols - 1) / cols;
        int atlasWidth = cols * tileWidth;
        int atlasHeight = rows * tileHeight;

        BufferedImage atlas = new BufferedImage(atlasWidth, atlasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = atlas.createGraphics();

        for (int i = 0; i < count; i++) {
            BufferedImage texture;
            try {
                texture = ImageIO.read(fileList.get(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int x = (i % cols) * tileWidth;
            int y = (i / cols) * tileHeight;

            float uMin = (float) x / atlasWidth;
            float uMax = (float) (x + tileWidth) / atlasWidth;
            float vMin = (float) y / atlasHeight;
            float vMax = (float) (y + tileHeight) / atlasHeight;
            String textureName = fileList.get(i).getName();
            Identifier textureId = Identifier.of(BetterParticles.MOD_ID, textureName);
            SpriteMeta meta = new SpriteMeta(textureId,uMin,uMax,vMin,vMax,tileWidth,tileHeight,x,y);
            AtlasSpriteManager.getInstance().addSpriteMeta(meta);
            g.drawImage(texture, x, y, null);
        }
        g.dispose();
        try {
            File outputDir = outputAtlas.resolve(BetterParticles.MOD_ID+"_atlas.png").toFile();
            ImageIO.write(atlas, "png", outputDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new AtlasProvider();
        AtlasSpriteManager.getInstance().AllMetaToSprite();
    }
}
