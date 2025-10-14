package com.vanilla.atlas;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vanilla.BetterParticles;
import com.vanilla.util.SendMessageToPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CustomTextureLoader {

    private static final Identifier PARTICLE_ATLAS_ID = Identifier.ofVanilla("textures/atlas/particles.png");

    public static Identifier readImageFile(String url){
        Path path = new File(url).toPath();
        Identifier id = copyImageToLocal(path);
        generateJsonToLocal(id);
        return id;
    }

    private static Identifier copyImageToLocal(Path source){
        Path imageDir = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("assets")
                .resolve(BetterParticles.MOD_ID)
                .resolve("textures")
                .resolve("particle");
       return copyImageToLocal(source,imageDir);
    }

    private static Identifier copyImageToLocal(Path source, Path target){
        try {
            Files.createDirectories(target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(source.toString());
        String name = file.getName();
        Path imageDir = target.resolve(name);
        try {
            Files.copy(source,imageDir, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int dotIndex = name.lastIndexOf('.');
        return Identifier.of(BetterParticles.MOD_ID,name.substring(0,dotIndex));
    }

    private static void generateJsonToLocal(Identifier id){
        Path jsonDir = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("assets")
                .resolve(BetterParticles.MOD_ID)
                .resolve("particles");
        try {
            Files.createDirectories(jsonDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject json = autoGenerateJson(id);
        Path jsonFile = jsonDir.resolve(id.getPath() + ".json");
        try(FileWriter writer = new FileWriter(jsonFile.toFile())){
            writer.write(json.toString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        SendMessageToPlayer.sendMessageToPlayer("自动注入成功!");
    }

    private static JsonObject autoGenerateJson(Identifier id){
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(id.toString());
        json.add("textures", array);
        return json;
    }

    public static SpriteProvider loadFromLocalFile(String name) throws IOException {
        Identifier customTextureId = Identifier.of(
                BetterParticles.MOD_ID,
                name
        );
        return loadFromLocalFile(customTextureId);
    }

    public static SpriteProvider loadFromLocalFile( Identifier customTextureId) throws IOException {
        MinecraftClient client = MinecraftClient.getInstance();
        TextureManager textureManager = client.getTextureManager();
        if (textureManager == null) {
            throw new IllegalStateException("纹理管理器未初始化");
        }
        SpriteAtlasTexture particleAtlas;
        if (textureManager.getTexture(PARTICLE_ATLAS_ID) instanceof SpriteAtlasTexture) {
            particleAtlas = (SpriteAtlasTexture) textureManager.getTexture(PARTICLE_ATLAS_ID);
        } else {
            particleAtlas = new SpriteAtlasTexture(PARTICLE_ATLAS_ID);
            textureManager.registerTexture(PARTICLE_ATLAS_ID, particleAtlas);
        }
        return new SpriteProvider() {
            @Override
            public Sprite getSprite(int age, int maxAge) {
                return particleAtlas.getSprite(customTextureId);
            }

            @Override
            public Sprite getSprite(Random random) {
                return particleAtlas.getSprite(customTextureId);
            }
        };
    }


}