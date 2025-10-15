package com.vanilla.atlas;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vanilla.BetterParticles;
import com.vanilla.util.SendMessageToPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CustomTextureLoader {
    private static boolean imageLoaded = false;
    private static boolean jsonLoaded = false;

    public static void readImageFile(String url){
        Path path = new File(url).toPath();
        Identifier id = copyImageToLocal(path);
        generateJsonToLocal(id);
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
        imageLoaded = true;
        afterFinished();
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
        jsonLoaded = true;
        afterFinished();
    }

    private static JsonObject autoGenerateJson(Identifier id){
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(id.toString());
        json.add("textures", array);
        return json;
    }

    private static void afterFinished(){
        if(jsonLoaded&&imageLoaded){
            jsonLoaded = false;
            imageLoaded = false;
            AtlasGenerator.generate();
        }
    }


}