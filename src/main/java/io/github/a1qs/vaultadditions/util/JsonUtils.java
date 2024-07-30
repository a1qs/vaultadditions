package io.github.a1qs.vaultadditions.util;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject readJsonFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeJsonFile(File file, JsonObject jsonObject) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(jsonObject, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
