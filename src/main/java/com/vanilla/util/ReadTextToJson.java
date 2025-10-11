package com.vanilla.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vanilla.function.CreateCircle;
import com.vanilla.function.CreateLine;
import com.vanilla.function.CreateSingleParticle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadTextToJson {
    private static Vec3d startPos = new Vec3d(0, 0, 0);

    public static Vec3d getStartPos() { return startPos; }

    public static List<JsonObject> readTextToJson(Path file) {
        if (MinecraftClient.getInstance().player != null) {
            startPos = MinecraftClient.getInstance().player.getPos();
        }
        List<JsonObject> list = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                list.add(JsonParser.parseString(line).getAsJsonObject());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void UseJsonToCreate(JsonObject json) {
        int mode = json.get("mode").getAsInt();
        switch (mode) {
            case 1:
                CreateSingleParticle.INSTANCE.toData(json);
                break;
            case 2:
                CreateLine.INSTANCE.toData(json);
                break;
            case 3:
                CreateCircle.INSTANCE.toData(json);
                break;
        }
    }
}
