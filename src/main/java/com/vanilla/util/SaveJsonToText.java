package com.vanilla.util;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SaveJsonToText {

    private static final String baseName = "particle_data_";
    private static final Pattern pattern = Pattern.compile(baseName + "(\\d+)\\.txt$");
    private static final SaveJsonToText instance = new SaveJsonToText();


    private BufferedWriter currentWriter;
    private int currentNumber;
    private Path currentFile;

    public static SaveJsonToText getInstance() { return instance; }

    private Path nextDataFile(Path folder) throws IOException {
        if (Files.notExists(folder)) {
            Files.createDirectories(folder);
        }
        int max = Files.list(folder)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .mapToInt(m -> Integer.parseInt(m.group(1)))
                .max()
                .orElse(0);
        currentNumber = max + 1;
        return folder.resolve(baseName + currentNumber + ".txt");
    }


    public synchronized void saveToTextFile(Path folder, JsonObject json) {
        try {
            if (currentWriter == null) {
                currentFile = nextDataFile(folder);
                currentWriter = Files.newBufferedWriter(currentFile,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }
            currentWriter.write(json.toString());
            currentWriter.newLine();
            currentWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToTextFile(JsonObject json) {
        saveToTextFile(getRootDir(), json);
    }

    public synchronized Path close() {
        if (currentWriter != null) {
            try { currentWriter.close(); } catch (IOException ignore) {}
            currentWriter = null;
        }
        return currentFile;
    }

    public static Path getRootDir(){
        Path root = FabricLoader.getInstance().getGameDir();
        return root.resolve("particles");
    }
}