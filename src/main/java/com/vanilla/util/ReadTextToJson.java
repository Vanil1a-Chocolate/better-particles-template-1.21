package com.vanilla.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadTextToJson {

    public static List<JsonObject> readTextToJson(Path file) {
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
}
